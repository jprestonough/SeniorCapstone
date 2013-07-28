<?PHP
ini_set('display_errors', '1');
error_reporting(E_ALL);
require_once("../includes/vars.php");
require_once("session_control.php");
require_once("employeeLogin_control.php");


$employeeLoginControl -> logout();

if(isset($_GET["source"]) && $_GET["source"] == "web"){
	$host  = $_SERVER['HTTP_HOST'];
	$uri   = rtrim(dirname($_SERVER['PHP_SELF']), '/\\');
	
	$extra = 'index.php';
	
	header("Location: http://$host$uri/$extra");
	
}

?>

