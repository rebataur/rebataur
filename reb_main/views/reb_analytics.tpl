<!doctype html>

<html>
	<head>
		<title>Rebataur Config</title>
		<script>
		</script>
		
		<style>


		</style>
		


	</head>


	<body>

<div>
	<a href="config">Configuration</a>
	<a href="wizard">Wizard</a>
	<a href="analytics">Analytics</a>
</div>
		<table>

			<thead>
				<tr>
					<th>Customer Name</th><th>Item Count</th>
				</tr>

				
			</thead>
			<tbody>
			% for i in val:
				<tr>
				<td>{{i[0]}}</td><td>{{i[1]}}</td>


				</tr>
			% end
			</tbody>


		</table>
	</body>


</html>
