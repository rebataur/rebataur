<!doctype html>

<html>
	<head>
		<title>Rebataur Config</title>
		<script>
		</script>
		
		<style>
			form > label{
				
				display:inline;
				padding:4px;
			}
			form > input {
				width:200px;
				display:block;
				padding:4px;

			}
			div.success{
				padding:1em;
				background:lightgreen;
			}
			div.failed{
				padding:1em;
				background:red;
			}
	
		</style>
		


	</head>


	<body>

<div>
	<a href="config">Configuration</a>
	<a href="wizard">Wizard</a>
	<a href="analytics">Analytics</a>
</div>
		

<h4>Postgres configuration</h4>
	
	% for k,v in val.iteritems():
		<div class="{{v}}">
			{{k}} -> {{v}}
		</div>
	% end

	<form method="POST" action="submit_config/pg">
		<label>server-address</label><input type ="text" name ="pgserver" value="localhost"/>
		<label>port</label><input type ="text" name ="pgport" value="5432"/>
		<label>db name</label><input type ="text" name ="pgdb" value="postgres"/>
		<label>user name</label><input type ="text" name ="pguser" value="postgres"/>
		<label>password</label><input type ="password" name ="pgpwd" value="postgres"/>
		<button type="submit" >Submit</button>
	</form>



<h4>Register Twitter Consumer Tokens</h4>
	<a href="https://apps.twitter.com/app/new">Twitter register and app for consumer token</a>
	

	<form method="POST" action="submit_config/twitter_srv">
		<label>access_token</label><input type ="text" name ="access_token" value=""/>
		<label>access_token_secret</label><input type ="text" name ="access_token_secret" value=""/>
		<label>consumer_key</label><input type ="text" name ="consumer_key" value=""/>
		<label>consumer_secret</label><input type ="text" name ="consumer_secret" value=""/>		
		<button type="submit" >Submit</button>
	</form>


<h4>AWS PG Config</h4>
	<form method="POST" action="submit_config/aws_pg_srv">
		<label>aws_pg_host</label><input type ="text" name ="aws_pg_host" value=""/>
		<label>aws_pg_port</label><input type ="text" name ="aws_pg_port" value=""/>
		<label>aws_pg_db</label><input type ="text" name ="aws_pg_db" value=""/>
		<label>aws_pg_user</label><input type ="text" name ="aws_pg_user" value=""/>
		<label>aws_pg_pwd</label><input type ="text" name ="aws_pg_pwd" value=""/>			
		<button type="submit" >Submit</button>
	</form>

<h4>Openweather API</h4>
	<form method="POST" action="submit_config/openweathermap_srv">
		<label>key</label><input type ="text" name ="key" value=""/>
					
		<button type="submit" >Submit</button>
	</form>
</body>


</html>
