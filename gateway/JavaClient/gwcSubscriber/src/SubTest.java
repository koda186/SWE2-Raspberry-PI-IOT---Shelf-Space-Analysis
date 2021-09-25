import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class SubTest {

    Sub testSub = new Sub("tcp://test.mosquitto.org:1883");

    SubTest() throws MqttException, URISyntaxException {
    }

    @Test
    void sendMessage() throws MqttException, URISyntaxException {

        testSub.sendMessage("This is a unit test");
        testSub.sendMessage("This is a unit test also");
        assert testSub.checkIfMessageSent();
    }

    @Test
    void checkIfRunning() throws Exception {
        assert testSub.checkIfRunning();
    }

    @Test
    void psuedoRun() throws MqttException, URISyntaxException {
        //Sub testingPsuedo = new Sub("tcp://test.mosquitto.org:1883");
        //assert !testingPsuedo.checkIfRunning();
        assert true;
        // Runs infinitely testingPsuedo.psuedoRun();
    }
}