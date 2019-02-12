<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.stream.Stream" %>

<a href="/" class="btn btn-default">Home</a>
<html>
<head>
  <link href='//fonts.googleapis.com/css?family=Marmelad' rel='stylesheet' type='text/css'>
  <title>Voting Application</title>
</head>
<body>
    <h1>Election Report</h1>
    
    <table>
    	<tr><th>All Voters:</th><td><%= request.getAttribute("totalVotes") %></td></tr>
    	<tr><th>Casted Votes:</th><td><%= request.getAttribute("totalCastedVotes") %></td></tr>
    	<tr><th>Voter Participation (%)</th><td><%= request.getAttribute("percent") %></td></tr>
    </table>
    
    <table>
    	<tr><th>Candidate Name</th><th>Faculty</th><th>Number of Votes</th></tr>
    	
    	<% Map<Entity, Integer> candidates = (Map<Entity, Integer>) request.getAttribute("candidatesVotesHash"); %>
    	<%  for(Map.Entry<Entity, Integer> e: candidates.entrySet()) { %>
    		<tr><td><%= e.getKey().getProperty("firstname") %> <%= e.getKey().getProperty("lastname") %> </td><td><%= e.getKey().getProperty("faculty") %> </td>
    		<td><%= e.getValue() %></td></tr>
    	<% } %>
    
    </table>
    
</body>
</html>
