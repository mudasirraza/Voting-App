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
		<h1>Students List</h1>
		<p><a href="/admin/students/new" class="btn btn-primary">Add new Student</a> | <a href="/admin/notify_students" class="btn btn-primary">Notify all students via email</a></p>
		
		<p>&nbsp;</p>

		<% Iterable<Entity> students = (Iterable<Entity>) request.getAttribute("students"); %>
		
		<table class="table table-bordered">
			<tr>
				<th>Email</th>
			</tr>		
			<% for(Entity s: students) { %>
				<tr>
					<td><%= s.getProperty("email") %></td>
				</tr>
			<% } %>
		</table>
	</div>
</body>
</html>