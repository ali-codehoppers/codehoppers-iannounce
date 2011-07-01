<?php
if(!(isset($_POST['username']))){
	echo "<forgotPassword>invalid username</forgotPass>";
}
else{
	if($_POST['username']=='awais')
		echo "<login><isloggedin>false</isloggedin><description>invalid username or password</description></login>";
	else
		echo "<login><isloggedin>true</isloggedin><sessionId>session1</sessionId></login>";
}
?>