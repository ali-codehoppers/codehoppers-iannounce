<?php
if(!(isset($_POST['username']))){
	echo "<forgotPassword> Invalid Username :P </forgotPassword>";
}
else{
	if($_POST['username']=='awa')
		echo "<forgotPassword>Email is sent :D</forgotPassword>";
	else
			echo "<forgotPassword>Invalid Username :P</forgotPassword>";
}
?>