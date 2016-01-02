from rebataur.utils.pgutil import PGService


class CreateExtn:

    def __init__(self):

        self.pgsrv = PGService()
        self.pgsrv.execute_ddl(extn_cons.create_meta_tables)

    def init_twitter_extn(self):
        access_token = self.pgsrv.execute_dml(
            "select key from service_reg where service_name = 'twitter_srv' and key_name = 								'access_token'")[0][0]

        access_token_secret = self.pgsrv.execute_dml(
            "select key from service_reg where service_name = 'twitter_srv' and key_name = 										'access_token_secret'")[0][0]

        consumer_key = self.pgsrv.execute_dml(
            "select key from service_reg where service_name = 'twitter_srv' and key_name = 								'consumer_key'")[0][0]

        consumer_secret = self.pgsrv.execute_dml(
            "select key from service_reg where service_name = 'twitter_srv' and key_name = 								'consumer_secret'")[0][0]

        twitter_sql = extn_cons.twitter_fdw_sql % (
            access_token, access_token_secret, consumer_key, consumer_secret)

        self.pgsrv.execute_ddl(twitter_sql)

    def init_aws_pg_extn(self):
        aws_pg_host = self.pgsrv.execute_dml(
            "select key from service_reg where service_name = 'aws_pg_srv' and key_name = 								'aws_pg_host'")[0][0]

        aws_pg_db = self.pgsrv.execute_dml(
            "select key from service_reg where service_name = 'aws_pg_srv' and key_name = 								'aws_pg_db'")[0][0]
        aws_pg_user = self.pgsrv.execute_dml(
            "select key from service_reg where service_name = 'aws_pg_srv' and key_name = 								'aws_pg_user'")[0][0]
        aws_pg_pwd = self.pgsrv.execute_dml(
            "select key from service_reg where service_name = 'aws_pg_srv' and key_name = 								'aws_pg_pwd'")[0][0]
        aws_pg_port = self.pgsrv.execute_dml(
            "select key from service_reg where service_name = 'aws_pg_srv' and key_name = 								'aws_pg_port'")[0][0]

        aws_pg = extn_cons.aws_pg_fdw_sql % (
            aws_pg_host, aws_pg_db, aws_pg_port, aws_pg_user, aws_pg_pwd)
        self.pgsrv.execute_ddl(aws_pg)

    def init_owm_extn(self):
        owm_key = self.pgsrv.execute_dml(
            "select key from service_reg where service_name = 'openweathermap_srv' and key_name = 	'key'")[0][0]
        owm_sql = (extn_cons.owm_fdw_sql) % (owm_key)
        self.pgsrv.execute_ddl(owm_sql)
