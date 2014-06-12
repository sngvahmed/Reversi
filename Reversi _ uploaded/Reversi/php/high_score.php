
<?php

	require("connect.php");
	
	$response = array();		
	$usr = $_POST["username"];
	$userdetails  = mysql_query("SELECT  high_score , played , won FROM  members WHERE username = '$usr' ")or die(mysql_error());			
	$result_data = array();
	
	if ($userdetails) {
			$row = mysql_fetch_array($userdetails);
			array_push($result_data , $row["high_score"]);
			array_push($result_data , $row["played"]);
			array_push($result_data , $row["won"]);
	} else {
		echo mysql_error();
	}
	
	$response['high'] = ($result_data);

	echo json_encode($response);
	
?>