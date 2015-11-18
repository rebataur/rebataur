import sqlite3
from bottle import route, run, debug, template, request,redirect, static_file, error
from os.path import expanduser
import os
from reb_main.pg_srv import PGService
import cons

home = expanduser("~")
sqlite_loc = home + "/rebconfig.db"



@route('/static/<filepath:path>')
def server_static(filepath):
	return static_file(filepath, root="./static")
	

@route('/')
def config():
    initialize()
    r = {"one":1,"two":2}
    return template('reb_config', val=r)


@route('/config')
def config():   
    result = {"pg_con":test_pg_conn()}
    return template('reb_config', val=result)


@route('/wizard')
def wizard():
    r = {"one":1,"two":2}
    return template('reb_wizard', val=r)


@route('/analytics')
def analytics():
    r = {"one":1,"two":2}
    return template('reb_analytics', val=r)

@route("/submit_config/<path:path>",method="POST")
def submit_config(path):
	if path == "pg":
		pgprops = ["pgserver","pgport","pgdb","pguser","pgpwd"]
	
		conn = sqlite3.connect(sqlite_loc)
		c = conn.cursor()
		sql = """ delete from config where name = 'pg' """
		c.execute(sql)
		conn.commit	
		for i in pgprops:
			sql = """insert into config(name,key,value) values('pg','%s','%s') """ %(i,request.forms.get(i)) 
			c.execute(sql)
		conn.commit()	
		rows = c.execute("select value from config").fetchall()
		c.close()
		redirect("/config")
	elif path == "twitter_srv":
		pg = get_pg_conn()			
		twitter_props = ["access_token","access_token_secret","consumer_key","consumer_secret"]
		sql = """ delete from service_reg where service_name = '%s' """ % path
		pg.execute_ddl(sql)		
		for i in twitter_props:	
			sql = """insert into service_reg(service_name,key_name,key, fk_id) values('%s','%s','%s',%s) """ % 					      (path,i,request.forms.get(i), 1 )
			pg.execute_ddl(sql)
		
		redirect("/config")
			
	elif path == "aws_pg_srv":
		pg = get_pg_conn()			
		aws_pg_props = ["aws_pg_host","aws_pg_port","aws_pg_db","aws_pg_user","aws_pg_pwd"]
		sql = """ delete from service_reg where service_name = '%s' """ % path
		pg.execute_ddl(sql)		
		for i in aws_pg_props:	
			sql = """insert into service_reg(service_name,key_name,key, fk_id) values('%s','%s','%s',%s) """ % 					      (path,i,request.forms.get(i), 2)
			pg.execute_ddl(sql)
		
		redirect("/config")
	elif path == "openweathermap_srv":
		pg = get_pg_conn()			
		openweathermap_props = ["key"]
		sql = """ delete from service_reg where service_name = '%s' """ % path
		pg.execute_ddl(sql)		
		for i in openweathermap_props:	
			sql = """insert into service_reg(service_name,key_name,key, fk_id) values('%s','%s','%s',%s) """ % 					      (path,i,request.forms.get(i), 3)
			pg.execute_ddl(sql)
		
		redirect("/config")
			
@route('/upload', method='POST')
def do_upload():
    save_path = home+"/reb_repository"

    if not os.path.exists(save_path):
	os.makedirs(save_path)

    category   = request.forms.get('category')
    upload     = request.files.get('upload')
    name, ext = os.path.splitext(upload.filename)
    if ext not in ('.csv','.tsv','.sql','.gz'):
        return 'File extension not allowed.'

 
    upload.save(save_path) # appends upload.filename automatically
    redirect("/wizard")


#-------------------------------------------------------------------------
def initialize():
	conn = sqlite3.connect(sqlite_loc)
	c = conn.cursor()
	c.execute(""" create table if not exists config(name varchar,key varchar,value varchar) """)
	conn.commit()
	c.close()
def get_pg_config():
	conn = sqlite3.connect(sqlite_loc)
	c = conn.cursor()
	rows = c.execute("select key,value from config where name='pg'").fetchall()
	conn.commit()
	c.close()
	
	return rows

def test_pg_conn():
	pg_config = get_pg_config()
    	pg_con = ""
    	try:   
    		pg = PGService(pg_config[0][1],pg_config[1][1],pg_config[2][1],pg_config[3][1],pg_config[4][1])
		pg.execute_dml("select 1")
		init_pg_db()
		pg_con = "success"
		
    	except:
		pg_con = "failed"
	return pg_con

def get_pg_conn():
	pg_config = get_pg_config()     	 
    	return PGService(pg_config[0][1],pg_config[1][1],pg_config[2][1],pg_config[3][1],pg_config[4][1])
	
		
def init_pg_db():
	pg = get_pg_conn()
	pg.execute_ddl(cons.init_pg_db)

run(host='localhost', port=8080,debug=True, reloader=True)	
