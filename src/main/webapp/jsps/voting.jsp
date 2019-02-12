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
		<h1>Election Voting</h1>
		
		<p>&nbsp;</p>

		<% Iterable<Entity> candidates = (Iterable<Entity>) request.getAttribute("candidates"); %>
		<% String token = (String) request.getAttribute("token"); %>
		
		<form role="form" action="/voting" method="post">
		<input type="hidden" name="token" value="<%= token %>">
		<div class="row">
			<p><h3>Select Candidate:</h3></p>
		</div>
		<table class="table table-bordered">
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Faculty</th>
				<th></th>
			</tr>		
			<% for(Entity c: candidates) { %>
				<tr>
					<td><%= c.getProperty("firstname") %></td><td> <%= c.getProperty("lastname") %></td> <td> <%= c.getProperty("faculty") %> </td>
					<td><input type="radio" name="c_id" value="<%= c.getKey().getId() %>"></td>
				</tr>
			<% } %>
			
			<a href="/" class="btn btn-default">Cancel</a>
			<button type="submit" class="btn btn-success">Save</button>
		</form>					
		
	</div>
</body>
</html>