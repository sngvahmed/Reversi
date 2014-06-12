<?php

	require("connect.php");
    
	$response = array();

	if(isset($_POST["username"]) && isset($_POST["password"]) ){
	
		$username = $_POST["username"];
		$password = $_POST["password"];
		
		$update = mysql_query("update members set login=0 where username = '$username' and password = '$password'");
			
			if($update){
				$response['success'] = 1;
				echo json_encode($response);
			}else {
				
				$response['success'] = 0;
				echo json_encode($response);
			
			}
	
	}
	
	
	


?>