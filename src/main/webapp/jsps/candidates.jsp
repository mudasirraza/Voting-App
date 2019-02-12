<%@ page import="com.google.appengine.api.datastore.Entity" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Voting Application</title>

<!-- Bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<a href="/" class="btn btn-default">Home</a>
		<h1>Candidates List</h1>
		<p><a href="/admin/candidates/new" class="btn btn-primary">Create new Candidate</a></p>
		
		<p>&nbsp;</p>

		<% Iterable<Entity> candidates = (Iterable<Entity>) request.getAttribute("candidates"); %>
		
		<table class="table table-bordered">
			<tr>
				<th>First Names</th><th>Last Names</th><th>Faculty</th>
			</tr>		
			<% for(Entity c: candidates) { %>
				<tr>
					<td><%= c.getProperty("firstname") %></td>
					<td><%= c.getProperty("lastname") %></td>
					<td><%= c.getProperty("faculty") %></td>
				</tr>
			<% } %>
		</table>
	</div>
</body>
</html>