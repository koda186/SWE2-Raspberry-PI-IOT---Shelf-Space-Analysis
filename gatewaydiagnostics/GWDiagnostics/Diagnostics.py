from datetime import datetime
from stopwatch import Stopwatch
import random
import string
import os
import sys


class Methods:

    def __init__(self, gw_id, choice):
        self.gw_id = gw_id
        self.choice = choice
        self.test_result = ""
        self.date_time = ""

    # generate date_time stamp
    @staticmethod
    def date_time():
        now = datetime.now()  # current date and time
        date_time = now.strftime("%m/%d/%Y, %H:%M:%S")
        return date_time

    # Generate Heartbeat for simulation
    def heartbeat_diagnostic(self):
        print("Diagnosing Heartbeat\n")
        heartbeat = "Green"
        # assign value to test result
        self.test_result = heartbeat
        return self.test_result

    # GW Storage Diagnostic - create a one-megabyte file with random characters. Use exception handling to
    # determine if there is an error. Report success or error to the asset manager. Error reports shall
    # include exception insights.
    def storage_diagnostic(self):
        print("Diagnosing storage")
        min_length = 5
        max_length = 30
        status = False

        file = 'file.txt'
        open(file, 'w')
        file_size = os.stat(file).st_size
        # stat module defines constants and functions for interpreting the results of os.stat()
        # st_size - size of file, in bytes,
        # try:
        with open(file, 'w') as myFile:
            while file_size < 1000000:
                length = random.randint(min_length, max_length)
                my_alphabets = ''.join(random.choice(string.ascii_lowercase) for x in range(length))
                myFile.write(my_alphabets)
                file_size = os.stat(file).st_size
                if file_size >= 1000000:
                    print('1mb written, lets read it')
                    status = True
                    break
            myFile.close()
        if status:
            self.test_result = "Green - Read content in the file successfully"
            return self.test_result
        else:
            diagnostic_test = "RED: Error, can't find file or read data"
            self.test_result = diagnostic_test
            return self.test_result

    # CPU Prime Number Test - Measures how fast the CPU can search for prime numbers.
    # example, 1, 2, 3, 5, 7, 11, etc. This algorithm should use a loop.
    # Report success or error to the asset manager. Error reports shall
    # include exception insights.
    @staticmethod
    def cpu_prime_number_diagnostic(n):
        stopwatch = Stopwatch()
        numbers = set(range(n, 1, -1))
        primes = []
        print("\nPerforming cpu_prime_number_diagnostics")
        while numbers:
            p = numbers.pop()
            primes.append(p)
            numbers.difference_update(set(range(p * 2, n + 1, p)))
        stopwatch.stop()  # stop stopwatch, time freezes
        print(primes)
        return stopwatch

    def get_time_result(self, time):
        print("getting result:")
        self.test_result = str(time)
        return self.test_result

    def get_memory_result(self, memory):
        print("getting result:")
        self.test_result = (str(memory) + " bytes")
        return self.test_result

    @staticmethod
    def get_cpu_floating_point_test():
        stopwatch1 = Stopwatch()
        fp_number_divisor = 0.03
        div = 0.00
        mult = 0.00
        for i in range(1000000):
            div += i/fp_number_divisor
            mult += i * fp_number_divisor
        stopwatch1.stop()  # stop stopwatch, time freezes
        print("div calculation performed" + str(div))
        print("mult calculation performed" + str(mult))
        return stopwatch1

    @staticmethod
    def get_cpu_integer_test():
        stopwatch1 = Stopwatch()
        int_number_divisor = 10000
        div = 0
        mult = 0
        for i in range(1000000):
            div += i/int_number_divisor
            mult += i * int_number_divisor
        stopwatch1.stop()  # stop stopwatch, time freezes
        print("div calculation performed" + str(div))
        print("mult calculation performed" + str(mult))
        return stopwatch1

    @staticmethod
    def get_memory_list():
        x = []
        for i in range(1000000):
            x.append([])

        num_bytes = sys.getsizeof(x)
        print(str(num_bytes) + " bytes")
        return num_bytes

    # Below Tests TODO:
    """
    # 8.	The Gateway Controller shall support the execution of multiple simultaneous processes (multithreading). 


    # 9.	GW Memory Diagnostic - create a list with one million items. Use python exception handling to determine 
            if there is an error. Report success or error. Error reports shall include exception insights. 

    """

    # putResult(GWId, timestamp, testType, testResult) #supports handling for multiple diagnostic tests!
    # #– upon completion of a diagnostic test, test results shall be reported to GWC for CIC.
    # This must follow the mongoose schema so that we can send and retrieve data from db easily
    # GWID: Number, TIMESTAMP: {type: Date, default: Date.now}, STATUS: String
    def put_result(self):
        # get date_time stamp for result
        now = datetime.now()  # current date and time
        date_time = now.strftime("%m/%d/%Y, %H:%M:%S")
        self.date_time = date_time

        test_result = {
            # 'GWID': int(self.gw_id),
            'GWID': self.gw_id,
            'TIMESTAMP': self.date_time,
            'TESTTYPE': self.choice,
            'STATUS': self.test_result
        }

        return test_result


# Initialize variables, console loop
def main(gw_id, choice):

    # get info based on gateway
    # print('\nWelcome to Diagnostics\n')
    diagnostics = Methods(gw_id, choice)
    if choice == "Heartbeat":
        diagnostics.heartbeat_diagnostic()
        heartbeat = diagnostics.put_result()
        return heartbeat
    elif choice == "Storage Diagnostic":
        diagnostics.storage_diagnostic()
        storage = diagnostics.put_result()
        return storage
    elif choice == "CPU Prime Number Test":
        print("Lets do Prime time for 1 Million")
        result = Methods.cpu_prime_number_diagnostic(1000000)
        diagnostics.get_time_result(result)
        cpu_prime_time = diagnostics.put_result()
        return cpu_prime_time
    elif choice == "CPU Floating Point Test":
        print("Lets do CPU Floating point test")
        result = diagnostics.get_cpu_floating_point_test()
        diagnostics.get_time_result(result)
        floating_point_time = diagnostics.put_result()
        return floating_point_time
    elif choice == "CPU Integer Test":
        print("Lets do CPU Integer test")
        result = diagnostics.get_cpu_integer_test()
        diagnostics.get_time_result(result)
        cpu_integer_time = diagnostics.put_result()
        return cpu_integer_time
    elif choice == "Memory Diagnostic":
        print("Lets do CPU Memory Diagnostic")
        result = diagnostics.get_memory_list()
        diagnostics.get_memory_result(result)
        cpu_memory = diagnostics.put_result()
        return cpu_memory
    else:
        print("Invalid Choice")




