import psycopg2

class PGService:

	def __init__(self):
		try:
			self.conn = psycopg2.connect("dbname='postgres' user='postgres' host='localhost' password='postgres'")
			self.conn.set_isolation_level(0)
			self.cur = self.conn.cursor()
			
		except:
			print "Not able to connect to database"

	def execute_ddl(self,query):
		self.cur.execute(query)
		
	
	def execute_dml(self,query):
		self.cur.execute(query)
		return self.cur.fetchall()
	
