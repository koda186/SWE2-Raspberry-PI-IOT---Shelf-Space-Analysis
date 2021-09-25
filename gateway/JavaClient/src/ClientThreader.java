import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sun.glass.ui.Application.run;

public class ClientThreader //Runs GWC process concurrently using fixed multithreading executor
{
    public static void main(String args[]) throws Exception {
        Sub dataSimClient = new Sub("tcp://test.mosquitto.org:1883");
        Client diagnosticsClient = new Client();
        subUpdate updateClient = new subUpdate("tcp://test.mosquitto.org:1883");
       //Initialization of all clients as threads

        ExecutorService es = Executors.newFixedThreadPool(3);
        es.execute(dataSimClient);
        es.execute(diagnosticsClient);
        es.execute(updateClient);
        //Concurrent execution of GWC clients

        es.shutdown();//shutdown once process finishes

    }//end of the main method

}//end of the ClientThreader class
