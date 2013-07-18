<?PHP
include("includes/header.php");

if ($loginControl -> checkLogin()) {
	$dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);

	$stmt = $dbh->prepare("select CustomerFirst,CustomerLast from Customer where CustomerEmail = ?;");
	$stmt->bindParam(1, $_SESSION['CustomerEmail']);
	$stmt->execute();
	
	$data = $stmt->fetchAll();
	echo '{"first":"'.$data[0][0].'","last":"'.$data[0][1].'","email":"'.$_SESSION['CustomerEmail'].'"}';
}
?>
