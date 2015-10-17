from multicorn import ForeignDataWrapper
import tweepy
from tweepy.streaming import StreamListener
from tweepy import Stream

from fdwtwitter.twitterfdwservice import TwitterService

# Twitter FDW
'''
drop server if exists  twitter_srv cascade;
CREATE SERVER twitter_srv foreign data wrapper multicorn options (
    wrapper 'rebfdw.TwitterFDW'
);

drop table if exists  twitter cascade;
CREATE FOREIGN TABLE twitter (
    tweet text
 
)server twitter_srv options(
	search_type 'hashtags',
	search_number  '10',
	search_text  'postgres',
	access_token  '',
	access_token_secret  '',
	consumer_key '',
	consumer_secret  ''
);

select * from twitter;
'''
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
	self.search_type = "user_timeline"
	self.search_number = 10
	self.search_text = ""
	for qual in quals :
		if qual.field_name == "search_type":
			self.search_type = qual.value
		elif qual.field_name == "limit":
			self.search_number = qual.value
		elif qual.field_name == "search_text":
			self.search_text = qual.value
	
	line = {}
	result = self.tservice.getTwitterData(self.search_type,self.search_number,self.search_text)
	for i in result:
		line["tweet_data"] = i
		line["search_type"] = self.search_type
		line["search_text"] = self.search_text 
		yield line
	
	
