import os
import urllib2
import commands

from multicorn import ForeignDataWrapper
import tweepy
from tweepy.streaming import StreamListener
from tweepy import Stream
from multicorn.utils import log_to_postgres
from logging import ERROR, WARNING

from reb_fdw.twitter_service import TwitterService
from reb_fdw.owm_service import OWMService
import hdp_cons
import hive_util
import rlang_util

rep_path = "/home/ranjan/reb_repository"

# Twitter FDW


class TwitterFDW(ForeignDataWrapper):

    def __init__(self, options, columns):
        super(TwitterFDW, self).__init__(options, columns)
        self.columns = columns
        self.access_token = options["access_token"]
        self.access_token_secret = options["access_token_secret"]
        self.consumer_key = options["consumer_key"]
        self.consumer_secret = options["consumer_secret"]
        self.tservice = TwitterService(
            self.access_token,
            self.access_token_secret,
            self.consumer_key,
            self.consumer_secret)

    def execute(self, quals, columns):
        # Put some defaults
        self.fn_name = "user_timeline"
        self.search_number = 10
        self.search_text = ""
        for qual in quals:
            if qual.field_name == "fn_name":
                self.fn_name = qual.value
            elif qual.field_name == "limit":
                self.search_number = qual.value
            elif qual.field_name == "search_text":
                self.search_text = qual.value

        line = {}
        result = self.tservice.getTwitterData(
            self.fn_name, self.search_number, self.search_text)
        for i in result:
            line["tweet_data"] = i
            line["fn_name"] = self.fn_name
            line["search_text"] = self.search_text
            yield line


#--------------OWM
class OWMFDW(ForeignDataWrapper):

    def __init__(self, options, columns):
        super(OWMFDW, self).__init__(options, columns)
        self.columns = columns
        self.key = options["key"]
        self.oservice = OWMService(self.key)

    def execute(self, quals, columns):
        # Put some defaults
        self.fn_name = "weather"
        self.search_number = 4

        for qual in quals:
            if qual.field_name == "fn_name":
                self.fn_name = qual.value
            elif qual.field_name == "city_name":
                self.city_name = qual.value
            elif qual.field_name == "country_name":
                self.country_name = qual.value
            elif qual.field_name == "limit":
                self.search_number = qual.value
        line = {}
        result = self.oservice.getOWMData(
            self.fn_name,
            self.city_name,
            self.country_name,
            self.search_number)
        for i in result:
            line["weather_data"] = i
            line["fn_name"] = self.fn_name
            line["city_name"] = self.city_name
            line["country_name"] = self.country_name
            yield line


#---------------Utilities

class Utilities(ForeignDataWrapper):

    def __init__(self, options, columns):
        super(Utilities, self).__init__(options, columns)
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


#---------------------Hadoop

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

        while not hive_util.isConnected():
            sleep(20)


    def execute(self, quals, columns):
        line = {}
        '''
		# check whether docker is running, if not then start it
		commands.getstatusoutput( self.options["docker_start"] )

		isRedoopRunning = bool ( commands.getstatusoutput( "docker inspect -f {{.State.Running}} rebdoop" ) )
		if isRedoopRunning is None or not isRedoopRunning:
			commands.getstatusoutput( self.options["docker_remove"] )
			commands.getstatusoutput( self.options["docker_start"] )

		'''
        while not hive_util.isConnected():
            sleep(20)
        data = hive_util.getHiveData(
            self.options["loadsql"],
            self.options["createsql"],
            self.options["dropsql"],
            self.options["selectsql"])
        for i in data:
            yield i
        '''
		data = [['Bank of Honolulu', 'Honolulu', 'HI', '21029', 'Bank of the Orient', '13-Oct-00', '17-Mar-05']]
		i =0
		return data
		for col in columns:
			line[col] = data[i]
			i += 1

		'''


#---------------------Rlang

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
        result = rlang_util.getRStats(self.fn_name, self.vals)

        line["fn_name"] = self.fn_name
        line["vals"] = self.vals
        line["result"] = result
        yield line
