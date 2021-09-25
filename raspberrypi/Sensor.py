
from threading import Thread
import datetime
import time
import _csv
from RPiPublisher import RPiPublisher



class Sensor(Thread):

    def __init__(self, group=None, target=None, name=None, args=(), kwargs=None,
                 daemon=False, id=0, dataSourceFileName=None):

        self.id = id
        self.dataSource = _csv.reader(open(dataSourceFileName, newline=""))
        self.publisher = RPiPublisher(name = "Pub{}".format(id))
        super(Sensor, self).__init__(group=group, target=target, name=name, args=args, kwargs=kwargs,
                                          daemon=daemon)
        # self.args = args
        # self.kwargs = kwargs


    # overriding this method in Thread
    def run(self):

        start = getTime()
        print("Thread %s beginning at %s" % (self.id, start))
        lineNum = 0
        for record in self.dataSource:
            # print("Thread %s: %s" % (self.id))
            if lineNum == 0:
                print("::".join(record))
            else:
                record.append(datetime.datetime.now().strftime("%m/%d/%Y, %H:%M:%S"))

                outStr = ",".join(record)

                print("SENDING %s" % outStr)
                self.publisher.publishData(outStr)

            lineNum+=1
            breakTime = 1
            time.sleep(breakTime)

        self.publisher.disconnect()
        end = getTime()
        print("Thread %s ending at %s" % (self.id, end))


def getTime():
    return datetime.datetime.today().strftime("%H:%M:%S")




