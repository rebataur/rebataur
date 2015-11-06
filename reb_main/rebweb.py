from bottle import route, run, template
import psycopg2 as pg


conn = pg.connect("user=postgres password=postgres host=localhost")
cur = conn.cursor()

@route('/hello/<name>')
def index(name):
  
    cur.execute("""
		select row_to_json (row) from (
			select * from twitter_followers
		)row
    """)
    
    rows = cur.fetchall()	
    return template('<b>Hello {{name}}</b>!', name=rows)

run(host='localhost', port=8080)	
