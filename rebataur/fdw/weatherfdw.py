from logging import ERROR

from multicorn import ForeignDataWrapper
from multicorn.utils import log_to_postgres

from rebataur.service.weatherservice import OWMService


class WeatherFDW(ForeignDataWrapper):

    def __init__(self, options, columns):
        super(WeatherFDW, self).__init__(options, columns)
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
