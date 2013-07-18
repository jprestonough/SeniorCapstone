<?PHP
include("includes/header.php");
/*
	Input: first,last,email,password
	Output: '{error:1}' if email already used

if($loginControl->create_acct())
	echo '{"error":false}';
else
	echo '{"error":true}';
*/
echo '{"error"="'.$loginControl->create_acct().'"}';
?>
