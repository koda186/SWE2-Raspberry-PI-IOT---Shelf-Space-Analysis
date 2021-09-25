# Gateway Diagnostics(GWD) #

This component of the WeIOT solution for Target store inventory data management is used to send Gateway Diagnostic Test Results. 
Gateway Diagnostic Tests are requested by the Asset Manager to check if Gateway system resources are in order.
Gateway Diagnostic Test Results are refined towards the Command and Control schema and sent to the Gateway Controller in order to report back to the associated Command and Control API on the backend to be stored and accessed
by asset managers. Programmed in Python using WebSocket libraries.

### Functionalities of the Gateway Diagnostics(GWD) ###

* Recieve message requests from Gateway Controller to perform system diagnostics
* Perform system diagnostics upon request from Gateway Controller to be reported back to CIC for health updates


### Gateway Diagnostics Client Server ###

* GWD Server establishes connection to GWC Client via WebSocket connection
* GWD Recieve Request from GWC and call upon our Diagnostics Methods Class to perform the requested tests 
* GWD Tests are then sent to our Diagnostics Methods Class to be perfomed
* GWD report back status of Tests requested via the established websocket Client/Server connection

### Diagnostics  ###

* GW Memory Diagnostic - creates a list with one million items. Reports success or error to the asset manager.
	
* GW Storage Diagnostic - creates a one-megabyte file with random characters. Reports success or error to the asset manager. 

* GW CPU Integer Math Test - Measures how fast the CPU can perform integer operations. An integer is a whole number with no fractional part. Reports success or error to the asset manager.

* GW CPU Prime Number Test - Measures how fast the CPU can search for prime numbers. example, 1, 2, 3, 5, 7, 11, etc. Reports success or error to the asset manager.

* GW CPU Floating Point Test - Performs the same operations as the integer math tests, however with floating point numbers. A floating point number is a number with fractional parts (e.g., 10.12345). Reports success or error to the asset manager. 

### How to Run ###

* Start the Diagnostics Websocket Server Client by running Diagnostics_Server 
* Run ClientThreader() to run the GWC client so the Diagnostics_Server can connect, receive requests to perform Diagnostic Tests and then report back results
* GWD will continue to run and recieve/process Diagnostic tests requests/results until a disconnect or timeout occurs