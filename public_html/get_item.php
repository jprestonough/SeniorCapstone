<?PHP
include("includes/header.php");
//$loginControl -> checkLogin() && 
if ($loginControl -> checkLogin() &&!empty($_POST['ItemID'])) {
	$dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);

	$stmt = $dbh->prepare("select Item.Name,Description,Price,Quantity,Department.Name from Item join Department on Item.Department_DepartmentID = Department.DepartmentID where ItemID = ?;");
	$stmt->bindParam(1, $_POST['ItemID']);
	$stmt->execute();
	
	$data = $stmt->fetchAll();

	if(count($data) > 0)
		echo '{"Name":"'.$data[0][0].'","Description":"'.$data[0][1].'","Price":"'.$data[0][2].'","Quantity":"'.$data[0][3].'","Department":"'.$data[0][4].'"}';
	else
		echo '{"Error":"true"}';
}
?>
