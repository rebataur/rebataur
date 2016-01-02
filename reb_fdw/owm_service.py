#!/usr/bin/env python

import pyowm


class OWMService:

    def __init__(self, key):
        self.key = key
        self.owm = pyowm.OWM(key)

    def getOWMData(self, fn_name, city_name, country_name, lmt):
        result = []
        city = ("%s,%s") % (city_name, country_name)
        if fn_name == "weather":
            obs = self.owm.weather_at_place(city)
            result.append(obs.get_weather().get_status())
        elif fn_name == "forecast":
            fc = self.owm.daily_forecast(city, limit=lmt)
            f = fc.get_forecast()
            weathers = f.get_weathers()
            for i in weathers:
                result.append(i.get_status())
        return result
