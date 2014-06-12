<?php

	require("connect.php");
    
	$response = array();

	if(isset($_POST["username"]) && isset($_POST["email"])){
	
		$username = $_POST["username"];
		$email = $_POST["email"];
		
		$select = mysql_query("select username from members where username = '$username'")or die(mysql_error());
		$select1 = mysql_query("select email from members where email = '$email'")or die(mysql_error());
		
		$response['username_exist'] = (mysql_num_rows($select) > 0)?1:0;
		$response['email_exist'] = (mysql_num_rows($select1) > 0)?1:0;
		
	    echo json_encode($response);
	
	
	}
	
	
	


?>