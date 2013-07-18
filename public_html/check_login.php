<?PHP
include("includes/header.php");
if ($loginControl -> checkLogin()) 
	echo '{"login":"true"}';
else
	echo '{"login":"false"}';

?>
