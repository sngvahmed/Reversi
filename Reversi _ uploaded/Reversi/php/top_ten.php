
<?php

	require("connect.php");
	
	$response = array();		
	$userdetails  = mysql_query("SELECT  username, won FROM  members ORDER BY won DESC limit 10")or die(mysql_error());			
	$result_data = array();
	if ($userdetails ) {
	  while($row = mysql_fetch_array($userdetails)) {
			array_push($result_data , $row["username"]);
			array_push($result_data , $row["won"]);
	  }
	}
	else {
	  echo mysql_error();
	}
	$response['Top'] = ($result_data);

	echo json_encode($response);
	
?>