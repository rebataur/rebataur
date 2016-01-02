from reb_main.pg_srv import PGService
import reb_main.cons


class FDWService:

    def __init__(self):
        self.pgsrv = PGService()

    def call_twitter_srv(self, fn_name, search_text, limit):
        twitter_query = (
            "insert into twitter_srv ( select * from fdw_twitter where fn_name = '%s' and search_text='%s' limit %s);") % (fn_name, search_text, limit)
        self.pgsrv.execute_ddl(twitter_query)

    def process_twitter_srv(self):
        self.pgsrv.execute_ddl(cons.process_twitter_sql)

    def call_owm_srv(self, fn_name, city_name, country_name):
        pass
