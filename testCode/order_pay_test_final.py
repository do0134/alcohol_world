import requests
import random
from concurrent.futures import ThreadPoolExecutor
import time
import redis
from collections import defaultdict

BASE_URL = "http://localhost:8085/api/v1/order"
min_v = 176
max_v = 185
my_dict = {i: 0 for i in range(min_v, max_v + 1)}
dont_pay = 0
error_type = defaultdict(int)


def send_heartbeat(userId, itemId):
    redis_conn = redis.Redis("localhost", 6379)
    channel_name = f"User {userId} order Item {itemId}"
    alive_response = channel_name + " is alive"
    p = redis_conn.pubsub()
    p.subscribe(["heartbeat"])

    for message in p.listen():
        if message['type'] == 'message':
            m = message['data'].decode('utf-8')
            if m == channel_name:
                redis_conn.publish("heartbeat", alive_response)
                break


def send_order_requests(userId):
    global dont_pay
    try:
        # 아이템 Id 뽑기
        itemId = random.randint(min_v, max_v)

        # Order 요청 보내기
        create_order_response = requests.post(f"{BASE_URL}/{userId}/{itemId}")
        create_order_result_code = create_order_response.json().get("resultCode")

        # Order 요청 실행 안됐으면 반환
        if create_order_result_code != "SUCCESS":
            error_type[create_order_result_code] += 1
            return

        # 20% 확률로 사용자는 결제하지 않음(요구사항)
        percentage = random.randint(0, 99)

        if percentage < 20:
            dont_pay += 1

        else:
            # 결제 창에 있다는 것을 알리는 HeartBeat 작동
            # send_heartbeat(userId, itemId)
            # 결제 요청 보내기
            pay_response = requests.post(f"{BASE_URL}/pay/{userId}/{itemId}")
            pay_result_code = pay_response.json().get("resultCode")
            if pay_result_code != "SUCCESS":
                error_type[pay_result_code] += 1
            else:
                error_type[pay_result_code] += 1
                my_dict[itemId] += 1
    except Exception as e:
        if "HTTPConnectionPool(host='localhost', port=8085): Max retries exceeded with url" in str(e):
            e = "HTTPConnectionPool(host='localhost', port=8085): Max retries exceeded with url"

        error_type[str(e)] += 1


def main():
    global my_dict
    # request보낼 수 == user의 수
    num_requests = 10000

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

    for key in error_type.keys():
        print(f"{key}: {error_type[key]}")

    print(f"Customer don't pay item {dont_pay}")


if __name__ == "__main__":
    main()
