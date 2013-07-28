<?PHP
ini_set('display_errors', '1');
error_reporting(E_ALL);
require_once("../includes/vars.php");
require_once("session_control.php");
require_once("employeeLogin_control.php");

if(isset($_POST["source"]) && $_POST["source"] == "web"){
	$host  = $_SERVER['HTTP_HOST'];
	$uri   = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
	
	//login(2) is 2 for manager... since only managers would login from the web. 
	if($employeeLoginControl -> login(2)){
		$extra = 'index.php';
	} else {
		$extra = 'index.php?note=fail';
	}
	header("Location: http://$host$uri/$extra");
	exit;
	
	
} else {
	if($employeeLoginControl -> login()){
		echo '{"login":true}';
	} else {
		echo '{"login":false}';
	}
}
?>
