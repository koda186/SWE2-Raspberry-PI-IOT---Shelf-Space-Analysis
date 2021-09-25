import paho.mqtt.client as mqtt
#publisher piece

class RPiPublisher(mqtt.Client):

    def __init__(self, name="SensorPublisher"):
        super(RPiPublisher, self).__init__(name)
        broker = "test.mosquitto.org"
        port = 1883
        self.connect(broker, port)
        self.easyname = name

    def on_log(client, userdata, level, buf):
        print("log: " + buf)

    # def on_connect(client, userdata, flags, rc):
    #     if rc == 0:
    #         # print("connected")
    #         # startPublishing(client)
    #     else:
    #         print("Bad connection; Returned code = ", rc)

    def on_disconnect(client, userdata, flags, rc=0):
        print("%s Disconnected result code %d" + (client.easyname, rc))

    def publishData(self, data):
        self.publish("evancook/sensor1", data)

