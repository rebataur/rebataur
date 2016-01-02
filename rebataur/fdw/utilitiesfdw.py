import commands
from logging import ERROR

from multicorn import ForeignDataWrapper
from multicorn.utils import log_to_postgres


class UtilitiesFDW(ForeignDataWrapper):

    def __init__(self, options, columns):
        super(UtilitiesFDW, self).__init__(options, columns)
        self.columns = columns
        self.options = options

    def execute(self, quals, columns):
        line = {}

        for qual in quals:
            if qual.field_name == "fn_name":
                self.fn_name = qual.value

            elif qual.field_name == "cmd":
                self.cmd = qual.value

        if self.fn_name == "exec":
            try:
                res = commands.getstatusoutput(self.cmd)
                line["fn_name"] = self.fn_name
                line["val"] = ""
                line["result"] = res
                line["cmd"] = self.cmd
                yield(line)
            except Exception as e:
                line["fn_name"] = self.fn_name
                line["val"] = ""
                line["result"] = "Error %s " % e
                yield(line)
                log_to_postgres(
                    "There was an error executing docker command Error: %s" %
                    e, ERROR, "Check your commands for errors")

