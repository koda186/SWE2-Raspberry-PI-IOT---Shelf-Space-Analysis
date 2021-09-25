import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


//Client side Programming
//To connect to other machine we need a socket connection. A socket connection means
//the two machines have information about each otherâ€™s network location (IP Address)
//and TCP port.The java.net.Socket class represents a Socket. To open a socket:

public class Client implements Runnable{

    private static boolean IsRunning = false;

    private static void Server_stopped()
    {
        IsRunning = false;
    }

    public boolean checkIfRunning(){return IsRunning;}


    public static void main(String[] args) {
        try{

            // -First argument Ã¢â‚¬â€œ IP address of Server. ( 127.0.0.1  is the IP address of
            // local-host, where code will run on single stand-alone machine).
            // -Second argument Ã¢â‚¬â€œ TCP Port. (Just a number representing which application to
            // run on a server. For example, HTTP runs on port 80. Port number can be from 0 to 65535)
            System.out.println("Client Started!");
            Socket socket = new Socket("localhost",8711);

            //getOutputStream() method is used to send the output through the socket.
            //getInputStream() method is used to send the input through the socket.
            DataOutputStream data_out = new DataOutputStream(socket.getOutputStream());
            DataInputStream data_in = new DataInputStream(socket.getInputStream());
            //BufferedReader json_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //int counter = 0;

            IsRunning = true;
            while(IsRunning) //main algorithim for run
            {

                //request heartbeat
                //This method writes a string to the underlying output stream
                //using modified UTF-8 encoding in a machine-independent manner.
                data_out.writeUTF("Client Requesting Heartbeat with GWId: 231");
                //Without a flush in there, you may wait forever
                //for the reply, because the request may still be in your buffer.
                data_out.flush(); //This method flushes data output stream.
                System.out.println("\n");

                //request cpu_storage_diagnostic
                //This method writes a string to the underlying output stream
                //using modified UTF-8 encoding in a machine-independent manner.
                data_out.writeUTF("Client Requesting CPU storage with GWId: 231");
                //Without a flush in there, you may wait forever
                //for the reply, because the request may still be in your buffer.
                data_out.flush(); //This method flushes data output stream.

                //request cpu_prime
                //This method writes a string to the underlying output stream
                //using modified UTF-8 encoding in a machine-independent manner.
                data_out.writeUTF("Client Requesting CPU prime time with GWId: 231");
                //Without a flush in there, you may wait forever
                //for the reply, because the request may still be in your buffer.
                data_out.flush(); //This method flushes data output stream.

                //request cpu_floating point
                //This method writes a string to the underlying output stream
                //using modified UTF-8 encoding in a machine-independent manner.
                data_out.writeUTF("Client Requesting CPU Floating Point time with GWId: 231");
                //Without a flush in there, you may wait forever
                //for the reply, because the request may still be in your buffer.
                data_out.flush(); //This method flushes data output stream.

                //request cpu_integer_test
                //This method writes a string to the underlying output stream
                //using modified UTF-8 encoding in a machine-independent manner.
                data_out.writeUTF("Client Requesting CPU Integer test time with GWId: 231");
                //Without a flush in there, you may wait forever
                //for the reply, because the request may still be in your buffer.
                data_out.flush(); //This method flushes data output stream.

                //request cpu_memory_diagnostic
                //This method writes a string to the underlying output stream
                //using modified UTF-8 encoding in a machine-independent manner.
                data_out.writeUTF("Client Requesting CPU Memory Diagnostic with GWId: 231");
                //Without a flush in there, you may wait forever
                //for the reply, because the request may still be in your buffer.
                data_out.flush(); //This method flushes data output stream.

                //readUTF reads in a string that has been encoded using a modified UTF-8 format.
                //The string of character is decoded from the UTF and returned as String.
                String json_data = data_in.readUTF();
                //Print string received from socket input data_in
                System.out.println("Receiving Diagnostic for: "+ "\n" + json_data);


                //Read Test Result
                //Print json received from socket input json_in
                String json_info = data_in.readUTF();
                //Print string received from socket input data_in
                System.out.println("Test Results Below: "+ "\n" + json_info);

                //Uncomment to send post to cic. Must turn on server.js on droplet with Node
                //TODO: fix post request
                //test post
                Methods.sendHeartbeatPost(json_info, "heartbeat");
                //System.out.println("Successfully sent heartbeat to CIC");

                if (json_info.contains("bye"))
                {
                    Server_stopped();
                    break;
                }

            }//end of algorithim while

            //close the connection by closing the socket as well
            //close input/output streams.
            data_out.close();
            data_in.close();
            socket.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }//end of main method


    @Override
    public void run() {

        //Multithreading runnable. Identical in functionality to main method
        try {

            // -First argument Ã¢â‚¬â€œ IP address of Server. ( 127.0.0.1  is the IP address of
            // local-host, where code will run on single stand-alone machine).
            // -Second argument Ã¢â‚¬â€œ TCP Port. (Just a number representing which application to
            // run on a server. For example, HTTP runs on port 80. Port number can be from 0 to 65535)
            System.out.println("Client Started!");
            Socket socket = new Socket("localhost", 8711);

            //getOutputStream() method is used to send the output through the socket.
            //getInputStream() method is used to send the input through the socket.
            DataOutputStream data_out = new DataOutputStream(socket.getOutputStream());
            DataInputStream data_in = new DataInputStream(socket.getInputStream());
            //BufferedReader json_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //int counter = 0;

            IsRunning = true;
            while (IsRunning) {

                //request heartbeat
                //This method writes a string to the underlying output stream
                //using modified UTF-8 encoding in a machine-independent manner.
                data_out.writeUTF("Client Requesting Heartbeat with GWId: 231");
                //Without a flush in there, you may wait forever
                //for the reply, because the request may still be in your buffer.
                data_out.flush(); //This method flushes data output stream.
                System.out.println("\n");

                //request cpu_storage_diagnostic
                //This method writes a string to the underlying output stream
                //using modified UTF-8 encoding in a machine-independent manner.
                data_out.writeUTF("Client Requesting CPU storage with GWId: 231");
                //Without a flush in there, you may wait forever
                //for the reply, because the request may still be in your buffer.
                data_out.flush(); //This method flushes data output stream.

                //request cpu_prime
                //This method writes a string to the underlying output stream
                //using modified UTF-8 encoding in a machine-independent manner.
                data_out.writeUTF("Client Requesting CPU prime time with GWId: 231");
                //Without a flush in there, you may wait forever
                //for the reply, because the request may still be in your buffer.
                data_out.flush(); //This method flushes data output stream.

                //request cpu_floating point
                //This method writes a string to the underlying output stream
                //using modified UTF-8 encoding in a machine-independent manner.
                data_out.writeUTF("Client Requesting CPU Floating Point time with GWId: 231");
                //Without a flush in there, you may wait forever
                //for the reply, because the request may still be in your buffer.
                data_out.flush(); //This method flushes data output stream.

                //request cpu_integer_test
                //This method writes a string to the underlying output stream
                //using modified UTF-8 encoding in a machine-independent manner.
                data_out.writeUTF("Client Requesting CPU Integer test time with GWId: 231");
                //Without a flush in there, you may wait forever
                //for the reply, because the request may still be in your buffer.
                data_out.flush(); //This method flushes data output stream.

                //request cpu_memory_diagnostic
                //This method writes a string to the underlying output stream
                //using modified UTF-8 encoding in a machine-independent manner.
                data_out.writeUTF("Client Requesting CPU Memory Diagnostic with GWId: 231");
                //Without a flush in there, you may wait forever
                //for the reply, because the request may still be in your buffer.
                data_out.flush(); //This method flushes data output stream.

                //readUTF reads in a string that has been encoded using a modified UTF-8 format.
                //The string of character is decoded from the UTF and returned as String.
                String json_data = data_in.readUTF();
                //Print string received from socket input data_in
                System.out.println("Receiving Diagnostic for: " + "\n" + json_data);


                //Read Test Result
                //Print json received from socket input json_in
                String json_info = data_in.readUTF();
                //Print string received from socket input data_in
                System.out.println("Test Results Below: " + "\n" + json_info);

                //Uncomment to send post to cic. Must turn on server.js on droplet with Node
                //TODO: fix post request
                //test post
                Methods.sendHeartbeatPost(json_info, "heartbeat");
                //System.out.println("Successfully sent heartbeat to CIC");

                // would need multithreading to keep process active to handle more than one request at once
                if (json_info.contains("bye")) {
                    Server_stopped();
                    break;
                }

            }//end of alrogithim while

            //close the connection by closing the socket as well
            //close input/output streams.
            data_out.close();
            data_in.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end of run method

}//end of Client class

