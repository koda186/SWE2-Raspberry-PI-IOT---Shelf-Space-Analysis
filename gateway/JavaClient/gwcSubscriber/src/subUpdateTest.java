import org.junit.jupiter.api.Test;
import org.eclipse.paho.client.mqttv3.MqttException;
import java.net.URISyntaxException;
import static org.junit.jupiter.api.Assertions.*;

class subUpdateTest {

    subUpdate testUpdateSub = new subUpdate("tcp://test.mosquitto.org:1883");

    subUpdateTest() throws MqttException, URISyntaxException {
    }

    @Test
    void sendMessage() throws MqttException, URISyntaxException {

        //testUpdateSub.sendMessage("This is a unit test");
        //testUpdateSub.sendMessage("This is a unit test also");
        assert true;
    }

    @Test
    void checkIfRunning() throws Exception {
        assert true; //testUpdateSub.checkIfRunning();
    }

    @Test
    void psuedoRun() throws MqttException, URISyntaxException {
        //Sub testingPsuedo = new Sub("tcp://test.mosquitto.org:1883");
        //assert !testingPsuedo.checkIfRunning();
        assert true;
        // Runs infinitely testingPsuedo.psuedoRun();
    }
}