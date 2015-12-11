
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

rep_path ="/home/ranjan/reb_repository"

# Twitter FDW
class TwitterFDW(ForeignDataWrapper):

   def __init__(self, options, columns):
        super(TwitterFDW, self).__init__(options, columns)
        self.columns = columns
	self.access_token =  options["access_token"]
	self.access_token_secret = options["access_token_secret"]
	self.consumer_key = options["consumer_key"]
	self.consumer_secret = options["consumer_secret"]
	self.tservice = TwitterService(self.access_token,self.access_token_secret,self.consumer_key,self.consumer_secret)

   def execute(self, quals, columns):
	#Put some defaults
	self.fn_name = "user_timeline"
	self.search_number = 10
	self.search_text = ""
	for qual in quals :
		if qual.field_name == "fn_name":
			self.fn_name = qual.value
		elif qual.field_name == "limit":
			self.search_number = qual.value
		elif qual.field_name == "search_text":
			self.search_text = qual.value
	
	line = {}
	result = self.tservice.getTwitterData(self.fn_name,self.search_number,self.search_text)
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
	self.key =  options["key"]
	self.oservice = OWMService(self.key)

   def execute(self, quals, columns):
	#Put some defaults
	self.fn_name = "weather"
	self.search_number = 4
	
	for qual in quals :
		if qual.field_name == "fn_name":
			self.fn_name = qual.value
		elif qual.field_name == "city_name":
			self.city_name = qual.value
		elif qual.field_name == "country_name":
			self.country_name = qual.value
		elif qual.field_name == "limit":
			self.search_number = qual.value
	line = {}
	result = self.oservice.getOWMData(self.fn_name,self.city_name,self.country_name,self.search_number)
	for i in result:
		line["weather_data"] = i
		line["fn_name"] = self.fn_name
		line["city_name"] = self.city_name 
		line["country_name"] = self.country_name 
		yield line
	
	
	
#---------------Utilities

class Utilities(ForeignDataWrapper):
	
	def __init__(self,options,columns):
		super(Utilities,self).__init__(options, columns)
		self.columns = columns
		self.options = options		

	def execute(self, quals, columns ):
		line = {}
		
		
		for qual in quals :
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
				log_to_postgres("There was an error executing docker command Error: %s" % e , ERROR,"Check your commands for errors")


