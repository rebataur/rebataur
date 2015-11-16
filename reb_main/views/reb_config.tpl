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
			div.status{
				padding:1em;
				border :1px solid green;
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
	<div class="status">
% for k,v in val.iteritems():
{{k}} -> {{v}}
% end
</div>
<form method="POST" action="submit_config/pg">

<label>server-address</label><input type ="text" name ="pgserver" value="localhost"/>
<label>port</label><input type ="text" name ="pgport" value="5432"/>
<label>db name</label><input type ="text" name ="pgdb" value="postgres"/>
<label>user name</label><input type ="text" name ="pguser" value="postgres"/>
<label>password</label><input type ="password" name ="pgpwd" value="postgres"/>
<button type="submit" >Submit</button>
</form>

	</body>


</html>
