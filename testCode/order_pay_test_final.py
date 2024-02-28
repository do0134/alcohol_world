import requests
import random
from concurrent.futures import ThreadPoolExecutor
import time
import redis


BASE_URL = "http://localhost:8085/api/v1/order"
min_v = 176
max_v = 185
my_dict = {i: 0 for i in range(min_v, max_v + 1)}
order_exception = 0
pay_exception = 0
error = 0
heartbeat_error = 0
dont_pay = 0


def send_heartbeat(redis_conn, userId, itemId):
    channel_name = f"User {userId} order Item {itemId}"
    p = redis_conn.pubsub()
    p.subscribe(channel_name)
    for message in p.listen():
        if message['type'] == 'message':
            m = message['data'].decode('utf-8')
            if m == channel_name:
                redis_conn.publish(channel_name, "alive")


def send_order_requests(userId):
    global order_exception, pay_exception, error, heartbeat_error, dont_pay
    try:
        # 아이템 Id 뽑기
        itemId = random.randint(min_v, max_v)
        
        # Order 요청 보내기
        create_order_response = requests.post(f"{BASE_URL}/{userId}/{itemId}")
        create_order_result_code = create_order_response.json().get("resultCode")
        # Order 요청 실행 안됐으면 반환
        if create_order_result_code != "SUCCESS":
            order_exception += 1
            print(f"Failed to create order for userId {userId} and itemId {itemId} because {create_order_result_code}")
            return

        # 20% 확률로 사용자는 결제하지 않음(요구사항)
        percentage = random.randint(0,99)

        if percentage < 20:
            dont_pay += 1

        else:
            r = redis.Redis("localhost", 6379)
            # 결제 창에 있다는 것을 알리는 HeartBeat 작동
            send_heartbeat(r,userId, itemId)
            # 결제 요청 보내기
            pay_response = requests.post(f"{BASE_URL}/pay/{userId}/{itemId}")
            r.pubsub().unsubscribe(f"User {userId} order Item {itemId}")
            r.close()
            pay_result_code = pay_response.json().get("resultCode")
            if pay_result_code != "SUCCESS":
                if pay_result_code == "NO_SUCH_ORDER":
                    heartbeat_error += 1
                else:
                    # print(f"Failed to pay for order for userId {userId} and itemId {itemId}")
                    pay_exception += 1
            else:
                my_dict[itemId] += 1
    except Exception as e:
        print(f"Error sending requests for userId {userId}: {e}")
        itemId = random.randint(min_v, max_v)
        error += 1
        r = redis.Redis("localhost", 6379)
        r.pubsub().unsubscribe(f"User {userId} order Item {itemId}")
        r.close()


def main():
    global my_dict, order_exception, pay_exception, error
    # request보낼 수 == user의 수
    num_requests = 1000

    start_time = time.time()

    # 동시 작업
    with ThreadPoolExecutor(max_workers=num_requests) as executor:
        for userId in range(1, num_requests + 1):
            executor.submit(send_order_requests, userId)

    # 시간 재기
    end_time = time.time()

    # 모든 error, 성공 수 기록
    elapsed_time = end_time - start_time
    print(f"Elapsed time: {elapsed_time} seconds")
    print(f"item used: {my_dict}")
    print(f"Failed because Exception: order {order_exception}, pay {pay_exception}")
    print(f"error: {error}")
    print(f"Customer don't pay item {dont_pay}")
    print(heartbeat_error)


if __name__ == "__main__":
    main()