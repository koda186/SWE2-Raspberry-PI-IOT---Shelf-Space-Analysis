
import org.eclipse.paho.client.mqttv3.*;

import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;



import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Scanner;

/**
 * A sample application that demonstrates how to use the Paho MQTT v3.1 Client blocking API.
 * Creates client infrastructure for sensor array data to be received.
 */
public class Sub implements MqttCallback, Runnable {

    //MQTT attributes
    private final int qos = 1;
    private String topic = "evancook/sensor1";
    private MqttClient client;
    private MqttMessage messageDoc;
    private boolean messageSent; //Check attributes
    private boolean isRunning;//meant to assist in multithreading

    public Sub(String uri) throws MqttException, URISyntaxException {
        this(new URI(uri));
    }

    public Sub(URI uri) throws MqttException {
        String host = String.format("tcp://%s:%d", uri.getHost(), uri.getPort());
        String[] auth = this.getAuth(uri);
        String username = auth[0];
        String password = auth[1];
        String clientId = MqttClient.generateClientId();
        if (!uri.getPath().isEmpty()) {
            this.topic = uri.getPath().substring(1);  //establish MQTT parameters
        }

        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName(username);
        conOpt.setPassword(password.toCharArray());

        this.client = new MqttClient(host, clientId, new MemoryPersistence());
        this.client.setCallback(this);
        this.client.connect();

        this.client.subscribe(this.topic, qos);

    }//end of default constructors

    private String[] getAuth(URI uri) {
        String a = uri.getAuthority();
        String[] first = a.split("@"); //Authorization access
        return first[0].split(":");

    }//end of getAuth method

    public void sendMessage(String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(qos);
        this.client.publish(this.topic, message); // Blocking publish

    }//end of the sendMesssage method

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost because: " + cause);
        System.exit(1);
        //Default disconnection handling

    }//end of the connectionLost method

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    /**
     * @see MqttCallback#messageArrived(String, MqttMessage)
     */
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
        documentMessage(message);
        this.messageSent = true; //Handles logic when a message is received through MQTT topic
        System.out.println(String.format("[%s] %s", topic, new String(message.getPayload())));


    }//end of the messageArrived method

    public void documentMessage(MqttMessage message)
    {
        this.messageDoc = message; //Saves received message from MQTT as attribute

    }//end of the documentMessage method

    public boolean checkIfMessageSent(){return this.messageSent;}

    public boolean checkIfRunning(){return isRunning;}   //Getters and setters for multithreading attributes

    public void changeRunning(boolean runState){isRunning = runState;}

    public void toggleMessageSent(boolean toggle)
    {
        this.messageSent = toggle;
    }

    public static void main(String[] args) throws Exception {

        Sub s = new Sub("tcp://test.mosquitto.org:1883");
        Date timestamp = new Date();
        s.sendMessage("1," + timestamp.getTime() + ",57," + "33"); //test payloads
        //s.sendMessage("Hello 2");
        while(s.client.isConnected()) //handles logic to send sensor data
        {
            if(s.messageSent == true)
            {
                String messageSent = new String(s.messageDoc.getPayload());
                Scanner messageParser = new Scanner(messageSent);

                while(messageParser.hasNextLine())//refines raw sensor data by converting to JSON string
                {
                    Scanner jsonScanner = new Scanner(messageParser.nextLine());
                    jsonScanner.useDelimiter(",");
                    String jsonString = "{";

                    for(int index = 0; index < 4; index++)
                    {
                        if(index == 0)
                            jsonString += "\"SHELFNUMBER\":\"" + jsonScanner.next() + "\",";
                        if(index == 2)
                            jsonString += "\"TIMESTAMP\":\"" + jsonScanner.next() + ",";
                        if(index == 3)
                            jsonString += jsonScanner.next() + "\"}";
                        if(index == 1)
                        {
                            jsonString += "\"SHELFWEIGHT\":\"" + jsonScanner.next() + "\",";

                        }

                    }//data refined here matches MongoDB Schema for sensor data

                    SubscriberMethods.sendDataPost(String.format("%s",jsonString),"data");//Sends data to CIC as post request.
                    s.toggleMessageSent(false);

                }//end of formatting while







            }

        }//end of connection algorithim while

    }//end of main method

    @Override
    public void run() {

        //Multithreading runnable. Identical in functionality to main method
        Sub s = null;
        try {  //try/catches to handle inherited exceptions
            s = new Sub("tcp://test.mosquitto.org:1883");
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Date timestamp = new Date();
        try {
            s.sendMessage("1," + timestamp.getTime() + ",57," + "33");
        } catch (MqttException e) {
            e.printStackTrace();
        }
        //s.sendMessage("Hello 2");
        while(s.client.isConnected())
        {
            if(s.messageSent == true)
            {
                String messageSent = new String(s.messageDoc.getPayload());
                Scanner messageParser = new Scanner(messageSent);

                while(messageParser.hasNextLine())
                {
                    Scanner jsonScanner = new Scanner(messageParser.nextLine());
                    jsonScanner.useDelimiter(",");
                    String jsonString = "{";

                    for(int index = 0; index < 4; index++)
                    {
                        if(index == 0)
                            jsonString += "\"SHELFNUMBER\":\"" + jsonScanner.next() + "\",";
                        if(index == 2)
                            jsonString += "\"TIMESTAMP\":\"" + jsonScanner.next() + ",";
                        if(index == 3)
                            jsonString += jsonScanner.next() + "\"}";
                        if(index == 1)
                        {
                            jsonString += "\"SHELFWEIGHT\":\"" + jsonScanner.next() + "\",";

                        }

                    }

                    try {
                        SubscriberMethods.sendDataPost(String.format("%s",jsonString),"data");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    s.toggleMessageSent(false);

                }//end of formatting while







            }

        }//end of connection algorithim while


    }//end of run method

}//end of Sub class

