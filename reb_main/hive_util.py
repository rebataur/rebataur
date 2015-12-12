import pyhs2

def getHiveData(loadsql,createsql,dropsql,selectsql):

	with pyhs2.connect(host='localhost',
        	           port=10000,
        	           authMechanism="PLAIN",
        	           user='root',
        	           password='test',
        	           database='default') as conn:
    		with conn.cursor() as cur:
    			#Show databases
    			#print cur.getDatabases()
			print("--------")

			cur.execute(dropsql)
			print("--------")
			cur.execute(createsql)	
			print("--------")
			cur.execute(loadsql)
			print("--------")			
			cur.execute(selectsql)
			print("--------")			
	  		#Return column info from query
       			#print cur.getSchema()

        		#Fetch table results
			result = []
        		for i in cur.fetch():
            			result.append(i)
				yield i


if __name__ == "__main__":	
	createsql  = """
		CREATE TABLE banklist (
		Bank  VARCHAR(90), 
		City VARCHAR(17), 
		ST VARCHAR(2), 
		CERT INT, 
		Acquiring  VARCHAR(65)  , 
		Closing  DATE, 
		Updated  DATE  
		) row format delimited fields terminated by ','
	"""

	loadsql = "LOAD DATA LOCAL INPATH '/repository/banklist.csv' OVERWRITE INTO TABLE banklist"
	dropsql = "DROP TABLE IF EXISTS banklist"
	selectsql = "select * from banklist"
	it = getHiveData(loadsql,createsql,dropsql,selectsql)
	print(it)
	
