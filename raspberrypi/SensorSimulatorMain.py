from Sensor import Sensor


def runSensors():
    sensor1 = Sensor(id = 1, dataSourceFileName= "MQTTDataCSV.csv")
    sensor2 = Sensor(id = 2, dataSourceFileName= "MQTTData2.csv")
    sensor3 = Sensor(id = 3, dataSourceFileName= "MQTTData3.csv")

    sensor1.start()
    sensor2.start()
    sensor3.start()



runSensors()