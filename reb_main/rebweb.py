from bottle import route, run, template, static_file

@route('/static/<filepath:path>')
def server_static(filepath):
	return static_file(filepath, root="./static")
	


@route('/home')
def index():
    r = {"one":1,"two":2}
    return template('metadata_template', val=r)

run(host='localhost', port=8080,debug=True, reloader=True)	
