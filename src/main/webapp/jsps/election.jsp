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
		<h1>Election Details</h1>
		
		<p>&nbsp;</p>
		
		<h3> The current start and end time of election are: </h3>
		<p><b> Start Time: </b> <%= request.getAttribute("startDate") %></p>
		<p><b> End Time: </b> <%= request.getAttribute("endDate") %></p>
		
		<h3> Use following form to update the start and end time of election. </h3>
		<form role="form" action="/admin/election" method="post">
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon">Start Date: </span>
					<input name="start_date" type="date" class="form-control">
					<span class="input-group-addon">Start Time: </span>
					<input name="start_time" type="time" class="form-control">
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon">End Date: </span>
					<input name="end_date" type="date" class="form-control">
					<span class="input-group-addon">End Time: </span>
					<input name="end_time" type="time" class="form-control">
				</div>
			</div>
			
			<a href="/" class="btn btn-default">Cancel</a>
			<button type="submit" class="btn btn-success">Save</button>
		</form>					
		
	</div>
</body>
</html>