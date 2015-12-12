from csvkit.utilities.csvsql import CSVSQL
import six

def getHiveSQL(args):
#args = ['--table', 'customer', '/home/ranjan/reb_repository/customer.csv']
	output_file = six.StringIO()
	utility = CSVSQL(args, output_file)
	utility.main()
	sql = output_file.getvalue()

	dropsql = "DROP TABLE IF EXISTS %s" % (args[1])
	createsql =  sql[:-2] + " row format delimited fields terminated by ','"
	loadsql = "LOAD DATA LOCAL INPATH '/repository/%s.csv' OVERWRITE INTO TABLE %s" % (args[1],args[1])

	#print dropsql
	#print createsql
	#print loadsql

	extn_fields = createsql[ createsql.index("(")+1: createsql.index(") row") ]
	#print extn_fields

	hdp_extn_sql = """

drop server if exists fdw_hadoop_srv cascade;
create server fdw_hadoop_srv foreign data wrapper multicorn	
options(
	wrapper 'reb_main.HadoopFDW'
);

drop foreign table if exists fdw_hdp_%s cascade;

create foreign table fdw_hdp_%s (
	%s
	
)server fdw_hadoop_srv options(
	createsql '%s',
	loadsql '%s',
	dropsql '%s',
	selectsql '%s',
	docker_start 'docker run  -d -v /home/ranjan/reb_repository:/repository -p 10000:10000 --name rebdoop rebdoop',
	docker_stop  'docker stop rebdoop',
	docker_remove 'docker rm rebdoop'

);

""" 	% (
		args[1],
		args[1],
		extn_fields,
		createsql.replace("'","''"),
		loadsql.replace("'","''"),
		dropsql.replace("'","''"),
		"select * from %s" % args[1] 		
	)

	print hdp_extn_sql
	return [loadsql,createsql,dropsql]
	
if __name__ == "__main__":
	args = ['--table', 'banklist', '/home/ranjan/reb_repository/banklist.csv']
	print ( getHiveSQL(args) )
