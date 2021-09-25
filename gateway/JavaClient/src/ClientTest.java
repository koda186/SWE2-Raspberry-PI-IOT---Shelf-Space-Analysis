import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @org.junit.jupiter.api.Test
    void checkIfRunning() {
        Client testingClient = new Client();
        assert !testingClient.checkIfRunning();
        //testingClient.psuedoRun();

    }

    @org.junit.jupiter.api.Test
    void main() {
        Client testingClient = new Client();
        assert true;

    }

    @org.junit.jupiter.api.Test
    void psuedoRun() throws Exception {
        Sub testingPsuedo = new Sub("tcp://test.mosquitto.org:1883");
        assert !testingPsuedo.checkIfRunning();
        assert true;
        // Runs infinitely testingPsuedo.psuedoRun();

    }
}