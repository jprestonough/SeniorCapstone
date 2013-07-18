<?PHP
include("includes/header.php");

// Input: email, password
if($loginControl -> login()){
	echo '{"login":true}';
} else {
	echo '{"login":false}';
}
?>
