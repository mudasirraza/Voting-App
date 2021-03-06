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
		<h1>New Candidate</h1>
		
		<p>&nbsp;</p>

		<form role="form" action="/admin/candidates" method="post">
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon">First Name</span>
					<input name="firstname" type="text" class="form-control">
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon">Last Name</span>
					<input name="lastname" type="text" class="form-control">
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon">Faculty</span>
					<input name="faculty" type="text" class="form-control">
				</div>
			</div>
			
			<a href="/admin/candidates" class="btn btn-default">Cancel</a>
			<button type="submit" class="btn btn-success">Save</button>
		</form>					
		
	</div>
</body>
</html>