


import org.eclipse.paho.client.mqttv3.*;

import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Scanner;

/**
 * A sample application that demonstrates how to use the Paho MQTT v3.1 Client blocking API.
 * Creates client infrastructure for firmware updates to be received.
 */
public class subUpdate implements MqttCallback, Runnable{

    //MQTT attributes
    private final int qos = 1; //quality of service, 0 is fire and forget, 1 is it
    // keeps sending data until it is received, 2 is it's going to send until it receives a response
    private String topic = "evancook/updatedFirmwareStatus";
    //private String topic = "evancook/sensor1";
    private MqttClient client;
    private MqttMessage messageDoc;
    private boolean messageSent;
    private boolean isRunning;

    public subUpdate(String uri) throws MqttException, URISyntaxException {
        this(new URI(uri));
    }

    private subUpdate(URI uri) throws MqttException {
        String host = String.format("tcp://%s:%d", uri.getHost(), uri.getPort());
        String[] auth = this.getAuth(uri);
        String username = auth[0];
        String password = auth[1];
        String clientId = MqttClient.generateClientId();
        if (!uri.getPath().isEmpty()) {
            this.topic = uri.getPath().substring(1); //establish MQTT parameters
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

    private void sendMessage(String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(qos);
        this.client.publish(this.topic, message); // Blocking publish

    }//end of sendMessage method

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost because: " + cause);
        System.exit(1);
        //Default disconnection handling

    }//end of connectionLost method

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


    }//end of messageArrived method

//------------------------------GWC implemented-----------------------------------------------------

    private void documentMessage(MqttMessage message)
    {
        this.messageDoc = message;
    }

    private void toggleMessageSent(boolean toggle)
    {
        this.messageSent = toggle;
    }

    public static void main(String[] args) throws Exception {

        subUpdate s = new subUpdate("tcp://test.mosquitto.org:1883");
        Date timestamp = new Date();
        //s.sendMessage("1 " + timestamp.getTime() + " 57"); //test payloads
        //s.sendMessage("Update firmware 1.6 ");
        System.out.println("== START SUBSCRIBER ==");
        System.out.println("== Awaiting Topic message to be published ==");
        while(s.client.isConnected()) //handles logic to update firmware
        {
            if(s.messageSent)
            {
                System.out.println("Topic == firmwareUpdate: Message published below: ");
                String messageSent = new String(s.messageDoc.getPayload());
                Scanner messageParser = new Scanner(messageSent);

                while(messageParser.hasNextLine()) //refines raw sensor data by converting to JSON string
                {
                    Scanner jsonScanner = new Scanner(messageParser.nextLine());
                    jsonScanner.useDelimiter(" ");
                    String jsonString = "{";

                    jsonString += "\"UPDATEDFIRMWARESTATUS\":\"" + jsonScanner.nextLine() +  "\"}";

                    subUpdateMethods.sendDataPost(String.format("%s",jsonString),"data"); //Sends data to CIC as post request.
                    //SubscriberMethods.sendDataPost(messageSent,"data");
                    s.toggleMessageSent(false);

                }//end of data refine while

            }

        }//end of algorithim while

    }//end of main method

    @Override
    public void run() {

        //Multithreading runnable. Identical in functionality to main method
        subUpdate s = null;
        try { //try/catches to handle inherited exceptions
            s = new subUpdate("tcp://test.mosquitto.org:1883");
        } catch (MqttException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        Date timestamp = new Date();
        //s.sendMessage("1 " + timestamp.getTime() + " 57");
        //s.sendMessage("Update firmware 1.6 ");
        System.out.println("== START SUBSCRIBER ==");
        System.out.println("== Awaiting Topic message to be published ==");
        while(s.client.isConnected())
        {
            if(s.messageSent)
            {
                System.out.println("Topic == firmwareUpdate: Message published below: ");
                String messageSent = new String(s.messageDoc.getPayload());
                Scanner messageParser = new Scanner(messageSent);

                while(messageParser.hasNextLine())
                {
                    Scanner jsonScanner = new Scanner(messageParser.nextLine());
                    jsonScanner.useDelimiter(" ");
                    String jsonString = "{";

                    jsonString += "\"UPDATEDFIRMWARESTATUS\":\"" + jsonScanner.nextLine() +  "\"}";

                    try {
                        subUpdateMethods.sendDataPost(String.format("%s",jsonString),"data");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //SubscriberMethods.sendDataPost(messageSent,"data");
                    s.toggleMessageSent(false);

                }//end of refinement while

            }

        }//end of algorithim while

    }//end of run method

}//end of the subUpdate class
