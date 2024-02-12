import requests
import random
from concurrent.futures import ThreadPoolExecutor
import time


def send_http_request(url, userId, itemId):
    global my_dict
    try:
        response = requests.put(f"{url}/{userId}/{itemId}")
        if response.status_code == 200:
            my_dict[itemId] += 1
    except Exception as e:
        print(f"Error sending request to {url} with userId {userId} and itemId {itemId}: {e}")



def main():
    global my_dict
    # Set the number of concurrent requests (N)
    num_requests = 10000
    my_dict = {i: 0 for i in range(1,11)}
    # Set the target URL
    base_url = "http://localhost:8084/api/v1/item/pay"

    start_time = time.time()

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


if __name__ == "__main__":
    main()
