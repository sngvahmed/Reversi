<?php

  require("connect.php");
	
  $response = array();

  if(isset($_POST["username"]) && isset($_POST["password"]) && isset($_POST["email"]) && isset($_POST["country"]) ) {
     
	 $username = $_POST["username"];
	 $pass = $_POST["password"];
	 $email = $_POST["email"];
	 $country = $_POST["country"];

	 $result = mysql_query("insert into members(username , password , email , country) values('$username' , '$pass' , '$email' , '$country')");
	 
	 if($result){
		
		$response["success"] = 1;
        $response["message"] = "member successfully added.";

        // echoing JSON response
        echo json_encode($response);
	 
	 }else {
	   
	   // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        

        echo json_encode($response);
	 
	 }

   }  
	
?>
