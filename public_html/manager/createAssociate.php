<?PHP
ini_set('display_errors', '1');
error_reporting(E_ALL);
require_once("../includes/vars.php");
require_once("session_control.php");
require_once("employeeLogin_control.php");


$type = $employeeLoginControl -> checkType();

if($type == 2) {
	if(isset($_POST["createEmployee"]) && $_POST["createEmployee"]=="new"){
			echo ($employeeLoginControl -> create_acct($_POST["type"],$_POST["password"]));
	} else {
		echo '{"success":"false","reason":"createAssociate.php"}';	
	}
} else {
	echo '{"success":"false","reason":"type"}';	
}

?>




