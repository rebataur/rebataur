import psycopg2

class PGService:

	def __init__(self,host='localhost',port='5432', dbname='postgres',user='postgres',password='postgres'):
		try:
			self.host = host
			self.port = port
			self.dbname = dbname
			self.user = user
			self.password = password
			self.constr = ("dbname='%s' user='%s' host='%s' password='%s'") % 								(self.dbname,self.user,self.host,self.password)
			self.conn = psycopg2.connect(self.constr)

			self.conn.set_isolation_level(0)
			self.cur = self.conn.cursor()			
		except:
			print "Not able to connect to database"

	def execute_ddl(self,query):
		self.cur.execute(query)		
	
	def execute_dml(self,query):
		self.cur.execute(query)
		return self.cur.fetchall()
	
