<?PHP
include("includes/header.php");

if($loginControl -> confirmUser()){
	echo 'Account confirmed!';
} else {
	echo 'Invalid code.';
}
?>
