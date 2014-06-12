<?php

	require("connect.php");
    
	$response = array();

	if(isset($_POST["username"]) && isset($_POST["password"]) ){
	
		$username = $_POST["username"];
		$password = $_POST["password"];
		$login = $_POST["password"];
		
		$select = mysql_query("select username,password,login from members where username = '$username' and password = '$password'")or die(mysql_error());
		$exist = mysql_fetch_array($select);

		if($exist['login']){
		
			$response['login'] = 1; // account already login now 
			$response['success'] = 0;
			echo json_encode($response);
		}
		else{
			$response['login'] = 0;
			$response['success'] = (mysql_num_rows($select) > 0)?1:0;
			if($response['success'] == 1){
				$update = mysql_query("update members set login=1 where username = '$username' and password = '$password'");
			}
			echo json_encode($response);
		}
	
	}
	
	
	


?>