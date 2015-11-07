import psycopg2
from reb_main.pg_srv import PGService
import fdw_cons

class CreateExtn:
	
	def __init__(self):		
			
		init_query = ("""
				--- create service_reg if not exists		
				create table if not exists service_reg( service_name varchar(16), key_name varchar(16), key text );
				create extension if not exists multicorn;
		""")
		self.pgsrv = PGService()
		self.pgsrv.execute_ddl(init_query)
		

	def init_twitter_extn(self):
		access_token = self.pgsrv.execute_dml("select key from service_reg where service_name = 'twitter_srv' and key_name = 								'access_token'")[0][0] 
		
 		access_token_secret = self.pgsrv.execute_dml("select key from service_reg where service_name = 'twitter_srv' and key_name = 								'access_token_secret'") [0][0]
		
		consumer_key = self.pgsrv.execute_dml("select key from service_reg where service_name = 'twitter_srv' and key_name = 								'consumer_key'")[0][0]
		
		consumer_secret = self.pgsrv.execute_dml("select key from service_reg where service_name = 'twitter_srv' and key_name = 								'consumer_secret'")[0][0]
 
		twitter_sql = fdw_cons.twitter_fdw_sql % (access_token, access_token_secret,consumer_key,consumer_secret)
				
		self.pgsrv.execute_ddl(twitter_sql)

	def init_aws_pg_extn(self):
		aws_pg_host = self.pgsrv.execute_dml("select key from service_reg where service_name = 'aws_pg_srv' and key_name = 								'aws_pg_host'")[0][0] 

		aws_pg_db = self.pgsrv.execute_dml("select key from service_reg where service_name = 'aws_pg_srv' and key_name = 								'aws_pg_db'")[0][0] 
		aws_pg_user = self.pgsrv.execute_dml("select key from service_reg where service_name = 'aws_pg_srv' and key_name = 								'aws_pg_user'")[0][0] 
		aws_pg_pwd = self.pgsrv.execute_dml("select key from service_reg where service_name = 'aws_pg_srv' and key_name = 								'aws_pg_pwd'")[0][0] 
		aws_pg_port = self.pgsrv.execute_dml("select key from service_reg where service_name = 'aws_pg_srv' and key_name = 								'aws_pg_port'")[0][0] 


		
		aws_pg = fdw_cons.aws_pg_fdw_sql % ( aws_pg_host,aws_pg_db,aws_pg_port,aws_pg_user,aws_pg_pwd)
		self.pgsrv.execute_ddl(aws_pg)
		
		

