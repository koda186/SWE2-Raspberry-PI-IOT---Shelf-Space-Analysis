
# import asyncio
import PythonServer.GWDiagnostics.Diagnostics
import socket
import json
import time


class Server:

    def __init__(self):
        self.is_running = True

    # stop sending heartbeat
    def stop_server(self):
        self.is_running = False
        return self.is_running

    # Server Programming
    # create a socket object
    soc = socket.socket()
    print("Socket successfully created")

    # reserve a port on your computer in our
    # case it is 2007 but it can be anything
    host = "localhost"
    port = 8710

    # Next bind to the port
    # we have not typed any ip in the ip field
    # instead we have inputted host
    # this makes the server listen to requests
    # coming from other computers on the network
    soc.bind((host, port))

    # put the socket into listening mode
    soc.listen(5)
    print("Socket is listening")

    # a forever loop until we interrupt it or
    # an error occurs
    conn, addr = soc.accept()
    print("Server started\n")
    print("Waiting for a client ... \n")
    print("Connection accepted with: ", addr)
    length_of_message = int.from_bytes(conn.recv(2), byteorder='big')
    # connection received, get message and length
    msg = conn.recv(length_of_message).decode("UTF-8")
    print('Received message: ' + msg)

    # create counter for loop
    counter = 0
    is_running = True

    # Diagnostic Test--------------------------------------------------------
    # search for string in message to determine diagnostics type.
    # get diagnostic based on choice (choice = type of diagnostic to be performed)
    if "Heartbeat" and "GWId: 231" in msg:
        # We send confimation message to client about what data we are retrieving
        # You must send length of message and then message
        while is_running:

            message_to_send = "\tHeartbeat".encode("UTF-8")
            conn.send(len(message_to_send).to_bytes(2, byteorder='big'))
            conn.send(message_to_send)

            # Secondly, we send the heartbeat diagnostic info - dictionary to json in bytes
            # TODO get gateway from client
            gw_id = "231"
            choice = "Heartbeat"
            DiagnosticTest = PythonServer.GWDiagnostics.Diagnostics.main(gw_id, choice)
            # print(DiagnosticTest)
            b = bytes(json.dumps(DiagnosticTest), 'utf-8')
            # print(b)
            conn.send((len(b)).to_bytes(2, byteorder='big'))
            conn.send(b)
            time.sleep(2)
            counter += 1
            if counter == 25:
                is_running = False
                message_to_send = "Stopping server".encode("UTF-8")
                conn.send(len(message_to_send).to_bytes(2, byteorder='big'))
                conn.send(message_to_send)
                print(message_to_send)
                message_to_send = "bye".encode("UTF-8")
                conn.send(len(message_to_send).to_bytes(2, byteorder='big'))
                conn.send(message_to_send)
                print(message_to_send)
    else:
        print("no message")
# END Diagnostic Test----------------------------------------------------------



