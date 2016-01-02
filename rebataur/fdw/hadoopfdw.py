import commands
from logging import ERROR
from time import sleep

from multicorn import ForeignDataWrapper
from multicorn.utils import log_to_postgres


from rebataur.utils import hiveutil

rep_path = "/home/ranjan/reb_repository"

class HadoopFDW(ForeignDataWrapper):

   def __init__(self, options, columns):
        super(HadoopFDW, self).__init__(options, columns)
        self.columns = columns
        self.options = options

        # check whether docker is running, if not then start it
        isRedoopRunning = commands.getstatusoutput(
            "docker inspect -f {{.State.Running}} rebdoop")[1]
        if isRedoopRunning == "<no value>":
            #commands.getstatusoutput( self.options["docker_stop"] )
            commands.getstatusoutput(self.options["docker_remove"])
            commands.getstatusoutput(self.options["docker_start"])

        while not hiveutil.isConnected():
            sleep(20)


   def execute(self, quals, columns):
        line = {}

        while not hiveutil.isConnected():
            sleep(20)

        while not hiveutil.isConnected():
            sleep(20)
        data = hiveutil.getHiveData(
            self.options["loadsql"],
            self.options["createsql"],
            self.options["dropsql"],
            self.options["selectsql"])
        for i in data:
            yield i

