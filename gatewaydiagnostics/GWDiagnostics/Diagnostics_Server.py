
import GWDiagnostics.Diagnostics
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
    port = 8711

    # Next bind to the port
    # we have not typed any ip in the ip field
    # instead we have inputted host
    # this makes the server listen to requests
    # coming from other computers on the network
    soc.bind((host, port))

    # put the socket into listening mode
    soc.listen(5)
    print("Socket is listening")

    # get the conn received address
    conn, addr = soc.accept()
    print("Server started\n")
    print("Waiting for a client ... \n")
    print("Connection accepted with: ", addr)

    # --------------------------Diagnostic Test (Heartbeat)---------------------------
    # Get first request
    length_of_message = int.from_bytes(conn.recv(2), byteorder='big')
    # connection received, get message and length
    msg = conn.recv(length_of_message).decode("UTF-8")
    print('\nReceived message: ' + msg)

    # create counter for loop for HB simulation loop since we dont have multi-threading
    counter = 0
    is_running = True
    # search for string in message to determine diagnostics type.
    # get diagnostic based on choice (choice = type of diagnostic to be performed)
    while is_running and "Heartbeat" in msg:
        message_to_send = "\tHeartbeat".encode("UTF-8")
        conn.send(len(message_to_send).to_bytes(2, byteorder='big'))
        conn.send(message_to_send)

        # Secondly, we send the heartbeat diagnostic info - dictionary to json in bytes
        # TODO get gateway from client
        gw_id = "231"
        choice = "Heartbeat"
        DiagnosticTest = GWDiagnostics.Diagnostics.main(gw_id, choice)
        # print(DiagnosticTest)
        b = bytes(json.dumps(DiagnosticTest), 'utf-8')
        # print(b)
        conn.send((len(b)).to_bytes(2, byteorder='big'))
        conn.send(b)
        time.sleep(2)
        counter += 1
        if counter == 9:
            break

    # --------------------------Diagnostic Test (CPU Storage)---------------------------
    # Get second request
    length_of_message2 = int.from_bytes(conn.recv(2), byteorder='big')
    # connection received, get message and length
    msg2 = conn.recv(length_of_message2).decode("UTF-8")
    print('\nReceived message: ' + msg2)

    if "CPU storage" in msg2:
        message_to_send = "\tstorage".encode("UTF-8")
        conn.send(len(message_to_send).to_bytes(2, byteorder='big'))
        conn.send(message_to_send)

        # Secondly, we send the CPU Storage diagnostic info - dictionary to json in bytes
        # TODO get gateway from client
        gw_id = "231"
        choice = "Storage Diagnostic"
        DiagnosticTest = GWDiagnostics.Diagnostics.main(gw_id, choice)
        print(DiagnosticTest)  #
        b = bytes(json.dumps(DiagnosticTest), 'utf-8')
        # print(b)  #
        conn.send((len(b)).to_bytes(2, byteorder='big'))
        conn.send(b)
        time.sleep(2)

    # --------------------------Diagnostic Test (CPU Prime time)---------------------------
    # Get second request
    length_of_message3 = int.from_bytes(conn.recv(2), byteorder='big')
    # connection received, get message and length
    msg3 = conn.recv(length_of_message3).decode("UTF-8")
    print('\nReceived message: ' + msg3)

    if "CPU prime" in msg3:
        message_to_send = "\tprime".encode("UTF-8")
        conn.send(len(message_to_send).to_bytes(2, byteorder='big'))
        conn.send(message_to_send)

        # Secondly, we send the CPU Storage diagnostic info - dictionary to json in bytes
        # TODO get gateway from client
        gw_id = "231"
        choice = "CPU Prime Number Test"
        DiagnosticTest = GWDiagnostics.Diagnostics.main(gw_id, choice)
        print(DiagnosticTest)  #
        b = bytes(json.dumps(DiagnosticTest), 'utf-8')
        # print(b)  #
        conn.send((len(b)).to_bytes(2, byteorder='big'))
        conn.send(b)
        time.sleep(2)

    # --------------------------Diagnostic Test (CPU Floating Point)---------------------------
    # Get second request
    length_of_message4 = int.from_bytes(conn.recv(2), byteorder='big')
    # connection received, get message and length
    msg4 = conn.recv(length_of_message4).decode("UTF-8")
    print('\nReceived message: ' + msg4)

    if "CPU Floating Point" in msg4:
        message_to_send = "\tFloating Point Test".encode("UTF-8")
        conn.send(len(message_to_send).to_bytes(2, byteorder='big'))
        conn.send(message_to_send)

        # Secondly, we send the CPU Storage diagnostic info - dictionary to json in bytes
        # TODO get gateway from client
        gw_id = "231"
        choice = "CPU Floating Point Test"
        DiagnosticTest = GWDiagnostics.Diagnostics.main(gw_id, choice)
        print(DiagnosticTest)  #
        b = bytes(json.dumps(DiagnosticTest), 'utf-8')
        # print(b)  #
        conn.send((len(b)).to_bytes(2, byteorder='big'))
        conn.send(b)
        time.sleep(2)

    # --------------------------Diagnostic Test (CPU Integer test)---------------------------
    # Get second request
    length_of_message5 = int.from_bytes(conn.recv(2), byteorder='big')
    # connection received, get message and length
    msg5 = conn.recv(length_of_message5).decode("UTF-8")
    print('\nReceived message: ' + msg5)

    if "CPU Integer" in msg5:
        message_to_send = "\tInteger Test".encode("UTF-8")
        conn.send(len(message_to_send).to_bytes(2, byteorder='big'))
        conn.send(message_to_send)

        # Secondly, we send the CPU Storage diagnostic info - dictionary to json in bytes
        # TODO get gateway from client
        gw_id = "231"
        choice = "CPU Integer Test"
        DiagnosticTest = GWDiagnostics.Diagnostics.main(gw_id, choice)
        print(DiagnosticTest)  #
        b = bytes(json.dumps(DiagnosticTest), 'utf-8')
        # print(b)  #
        conn.send((len(b)).to_bytes(2, byteorder='big'))
        conn.send(b)
        time.sleep(2)

    # --------------------------Diagnostic Test (Memory Diagnostic test)---------------------------
    # Get second request
    length_of_message6 = int.from_bytes(conn.recv(2), byteorder='big')
    # connection received, get message and length
    msg6 = conn.recv(length_of_message6).decode("UTF-8")
    print('\nReceived message: ' + msg6)

    if "Memory Diagnostic" in msg6:
        message_to_send = "\tMemory Diagnostic Test".encode("UTF-8")
        conn.send(len(message_to_send).to_bytes(2, byteorder='big'))
        conn.send(message_to_send)

        # Secondly, we send the CPU Storage diagnostic info - dictionary to json in bytes
        # TODO get gateway from client
        gw_id = "231"
        choice = "Memory Diagnostic"
        DiagnosticTest = GWDiagnostics.Diagnostics.main(gw_id, choice)
        print(DiagnosticTest)  #
        b = bytes(json.dumps(DiagnosticTest), 'utf-8')
        # print(b)  #
        conn.send((len(b)).to_bytes(2, byteorder='big'))
        conn.send(b)
        time.sleep(2)
    # -------------------END Diagnostic Test---------------------------------------

    # --------------------------Stop server simulation---------------------------
    is_running = False
    message_to_send = "server stopped".encode("UTF-8")
    conn.send(len(message_to_send).to_bytes(2, byteorder='big'))
    conn.send(message_to_send)
    print(message_to_send)
    message_to_send = "bye".encode("UTF-8")
    conn.send(len(message_to_send).to_bytes(2, byteorder='big'))
    conn.send(message_to_send)
    print(message_to_send)




