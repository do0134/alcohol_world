import requests
import random
from concurrent.futures import ThreadPoolExecutor
import time


BASE_URL = "http://localhost:8085/api/v1/order"
min_v = 176
max_v = 185
my_dict = {i: 0 for i in range(min_v, max_v + 1)}
order_exception = 0
pay_exception = 0
error = 0
heartbeat_error = 0
dont_pay = 0


def send_order_requests(userId):
    global order_exception, pay_exception, error, heartbeat_error, dont_pay
    try:
        # Choose a random itemId between MIN_ITEM_ID and MAX_ITEM_ID
        itemId = random.randint(min_v, max_v)
        # Create order
        create_order_response = requests.post(f"{BASE_URL}/{userId}/{itemId}")
        create_order_result_code = create_order_response.json().get("resultCode")
        if create_order_result_code != "SUCCESS":
            order_exception += 1
            print(f"Failed to create order for userId {userId} and itemId {itemId} because {create_order_result_code}")
            return

        # Random failed
        percentage = random.randint(0,99)

        if percentage < 20:
            dont_pay += 1
            return

        # Pay for order
        pay_response = requests.post(f"{BASE_URL}/pay/{userId}/{itemId}")
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
        error += 1


def main():
    global my_dict, order_exception, pay_exception, error
    # Set the number of concurrent requests (N)
    num_requests = 1000

    start_time = time.time()

    # Create a ThreadPoolExecutor to send concurrent requests
    with ThreadPoolExecutor(max_workers=num_requests) as executor:
        for userId in range(1, num_requests + 1):
            executor.submit(send_order_requests, userId)

    # Stop the timer
    end_time = time.time()

    # Calculate and print the elapsed time
    elapsed_time = end_time - start_time
    print(f"Elapsed time: {elapsed_time} seconds")
    print(f"item used: {my_dict}")
    print(f"Failed because Exception: order {order_exception}, pay {pay_exception}")
    print(f"error: {error}")
    print(f"Customer don't pay item {dont_pay}")


if __name__ == "__main__":
    main()