<!doctype html>

<html>
	<head>
		<title>Rebataur Config</title>
		<script>
		</script>
		
		<style>
			div.rep_files{
				padding:0.4em;
			}
			div.rep_files > a{
				padding-left:100px;
			}
		</style>
		


	</head>


	<body>

<div>
	<a href="config">Configuration</a>
	<a href="wizard">Wizard</a>
	<a href="analytics">Analytics</a>
</div>
		
	<form action="/upload" method="post" enctype="multipart/form-data">
  Category:      <input type="text" name="category" />
  Select a file: <input type="file" name="upload" />
  <input type="submit" value="Start upload" />
</form>

<h4>Uploaded Files</h4>
<table>
% for i in val["rep_files"]:
	
	<tr class="rep_files">
		<td>{{i}}</td>
		<td><a href="/process_rep_file?rep_file_name={{i}}">Process</a></td>
	</tr>
% end
</body>


</html>
