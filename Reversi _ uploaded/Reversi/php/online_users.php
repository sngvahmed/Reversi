
<?php

	require("connect.php");
	
	$response = array();		
	$userdetails  = mysql_query("SELECT  username FROM  members WHERE login='1'")or die(mysql_error());			
	$result_data = array();
	if ($userdetails ) {
	  while($row = mysql_fetch_array($userdetails)) {
			array_push($result_data , $row["username"]);
	  }
	}
	else {
	  echo mysql_error();
	}
	$response['online'] = ($result_data);

	echo json_encode($response);
	
?>