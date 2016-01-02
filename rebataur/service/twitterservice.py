#!/usr/bin/env python
# Variables that contains the user credentials to access Twitter API
import tweepy
from tweepy.streaming import StreamListener
from tweepy import Stream
import json


class TwitterService:

    def __init__(self, token, token_secret, key, key_secret):
        self.access_token = token
        self.access_token_secret = token_secret
        self.consumer_key = key
        self.consumer_secret = key_secret
        self.auth = tweepy.OAuthHandler(
            self.consumer_key, self.consumer_secret)
        self.auth.set_access_token(self.access_token, self.access_token_secret)
        self.api = tweepy.API(self.auth)

    def getTwitterData(self, fn_name, limit, search_text):
        if fn_name == "home_timeline":
            result = []
            for data in tweepy.Cursor(self.api.home_timeline).items(limit):
                for attr, value in data.__dict__.iteritems():
                    if attr == "_json":
                        result.append(json.dumps(value))
            return result
        elif fn_name == "user_timeline":
            result = []
            for data in tweepy.Cursor(self.api.user_timeline).items(limit):
                for attr, value in data.__dict__.iteritems():
                    if attr == "_json":
                        result.append(json.dumps(value))
            return result

        elif fn_name == "retweets_of_me":
            result = []
            for data in tweepy.Cursor(self.api.retweets_of_me).items(limit):
                for attr, value in data.__dict__.iteritems():
                    if attr == "_json":
                        result.append(json.dumps(value))
            return result
        elif fn_name == "followers":
            result = []
            for data in tweepy.Cursor(self.api.followers).items(limit):
                for attr, value in data.__dict__.iteritems():
                    if attr == "_json":
                        result.append(json.dumps(value))
            return result
        elif fn_name == "friends":
            result = []
            for data in tweepy.Cursor(self.api.friends).items(limit):
                for attr, value in data.__dict__.iteritems():
                    if attr == "_json":
                        result.append(json.dumps(value))
            return result
        elif fn_name == "search":
            result = []
            for data in tweepy.Cursor(
                    self.api.search,
                    q=search_text,
                    lang='en').items(limit):
                for attr, value in data.__dict__.iteritems():
                    if attr == "_json":
                        result.append(json.dumps(value))
            return result
