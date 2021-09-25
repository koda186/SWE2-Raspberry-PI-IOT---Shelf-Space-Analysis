# Gateway Controller #

This component of the WeIOT solution for Target store inventory data management is used to send data recieved from inventory sensory arrays (on shelfs)
that has been refined towards the Command and Control schema to the associated Command and Control API on the backend in order to be stored and accessed
by inventory managers. Programmed in Java with MQTT and WebSocket libraries and multi-threading runnable dependencies.

### Functionalities of the Gateway Controller ###

* Recieve, refine, and send sensor array data to CIC for storage and access
* Perform system diagnostics to be sent to CIC for health updates
* Recieve and send firmware upadate to the data sensor array

### Client Recieving and Sending Sensor Data ###

* Connection to sensor array is established through MQTT broker client
* Data is sent continueosly through MQTT publisher to GWC subscriber in data file format
* Recieved data is processed line by line and converted in to JSON format corresponding to CIC sensor data schema
* Refined data is sent to CIC Rest API via HTTP Post Request

### Client Performing System Diagnostics ###

* Request from CIC is sent to Gateway Diagnostics function in GWC via HTTP Get Request
* GWD establishes connection to System Diagnostics client via WebSocket connection
* Tests and diagnostics are performed on client side
* GWC Diagnostics client send JSON formatted results to CIC Rest API for documentation and status updates via HTTP Post Request

### Client Recieving and Sending Firmware Update ###

* Firmware update request from CIC is sent to Gateway Controller via HTTP Post Request
* Request and associated data is processed by client and sent through MQTT broker to sensor array

### How to Run ###

* Multithreading runnables implemented into all clients of GWC to run concurrently
* Run ClientThreader() to run all GWC clients
* GWC will continue to run and recieve/process data/requests until a disconnect or timeout occurs