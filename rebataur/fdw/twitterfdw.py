from logging import ERROR

from multicorn import ForeignDataWrapper
from multicorn.utils import log_to_postgres

from rebataur.service.twitterservice import TwitterService


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
