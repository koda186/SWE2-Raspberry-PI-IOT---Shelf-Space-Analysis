
import json
from datetime import datetime


class Methods:

    def __init__(self, gw_id, choice):
        self.gw_id = gw_id
        self.choice = choice
        self.test_result = ""

    def heartbeat_diagnostic(self):
        print("Diagnosing Heartbeat")
        heartbeat = "Green"
        # assign value to test result
        self.test_result = heartbeat
        return self.test_result

    # future TODO
    # putResult(GWId, timestamp, testType, testResult)
    # #– upon completion of a diagnostic test, test results shall be reported to CIC.
    def put_result(self):
        test_result = {
            'GWId': self.gw_id,
            'timestamp': '3:44:24Sept2019',
            'testType': self.choice,
            'testResult': self.test_result
        }

        return test_result


# Initialize variables, console loop
def main(gw_id, choice):

    # get info based on gateway
    print('\nWelcome to Diagnostics\n')
    diagnostics = Methods(gw_id, choice)
    if choice == "Heartbeat":
        diagnostics.heartbeat_diagnostic()
        heartbeat = diagnostics.put_result()
        return heartbeat
    else:
        print("Invalid Choice")

    #data_loaded = json.loads(data)  # data loaded


