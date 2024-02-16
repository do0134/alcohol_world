import requests
import random
from concurrent.futures import ThreadPoolExecutor
import time


def send_http_request(url, userId, itemId):
    global my_dict, error, real_error
    try:
        body = {
            "userId": userId,
            "itemId": itemId
        }
        # POST 요청 시 json 매개변수에 데이터 전달
        response = requests.post(f"{url}/{userId}/{itemId}", json=body)
        data = response.json()
        result_code = data.get("resultCode")

    except Exception as e:
        real_error += 1
        print(f"Error sending request to {url} with userId {userId} and itemId {itemId}: {e}")


def main():
    global my_dict, error, real_error
    num_requests = 10000
    my_dict = {i: 0 for i in range(1,11)}
    base_url = "http://localhost:8085/api/v1/order"

    start_time = time.time()
    error = 0
    real_error = 0
    # Create a ThreadPoolExecutor to send concurrent requests
    with ThreadPoolExecutor(max_workers=num_requests) as executor:
        # Use a list comprehension to create a list of tasks
        tasks = [executor.submit(send_http_request, base_url, userId, random.randint(1, 10)) for userId in
                 range(1, num_requests + 1)]

        # Wait for all tasks to complete
        for future in tasks:
            future.result()

    # Stop the timer
    end_time = time.time()

    # Calculate and print the elapsed time
    elapsed_time = end_time - start_time
    print(f"Elapsed time: {elapsed_time} seconds")
    print(f"item used: {my_dict}")
    print(f"error: {error}")
    print(real_error)


if __name__ == "__main__":
    main()
