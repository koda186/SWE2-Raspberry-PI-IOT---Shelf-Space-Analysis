
import org.eclipse.paho.client.mqttv3.*;

//Class made to send sample data through firmware Update channel.
public class pubUpdate {

    public static void main(String[] args) throws MqttException {

        String messageString = "1.7";

        if (args.length == 2 ) {
            messageString = args[1];
        }


        System.out.println("== START PUBLISHER ==");

        //Establish MQTT Client for publish of firmware request and update.
        MqttClient client = new MqttClient("tcp://test.mosquitto.org:1883", MqttClient.generateClientId());
        client.connect();
        MqttMessage message = new MqttMessage();
        message.setPayload(messageString.getBytes());
        client.publish("evancook/updateFirmwareRequest", message);

        System.out.println("\tMessage '"+ messageString +"' to 'evancook/updateFirmwareRequest'");
        //Log to console to confirm

        //client.disconnect();

        System.out.println("== END PUBLISHER ==");

    }//end of main method


}//end of pubUpdate class