from logging import ERROR

from multicorn import ForeignDataWrapper
from multicorn.utils import log_to_postgres

from rebataur.utils import rlangutil

rep_path = "/home/ranjan/reb_repository"


class RlangFDW(ForeignDataWrapper):

    def __init__(self, options, columns):
        super(RlangFDW, self).__init__(options, columns)
        self.columns = columns

    def execute(self, quals, columns):
        # Put some defaults
        self.fn_name = "hist"

        for qual in quals:
            if qual.field_name == "fn_name":
                self.fn_name = qual.value
            elif qual.field_name == "vals":
                self.vals = qual.value

        line = {}
        result = rlangutil.getRStats(self.fn_name, self.vals)

        line["fn_name"] = self.fn_name
        line["vals"] = self.vals
        line["result"] = result
        yield line
