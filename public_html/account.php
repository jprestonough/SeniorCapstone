<?PHP
include("includes/header.php");
if ($loginControl -> checkLogin()) {
	switch ($_POST['function']) {
		case 'change_email':
			if(empty($_POST['email']) || !preg_match(LoginControl::EMAIL,$_POST['email'])){
				echo '{"error":"1"}';
				break;
			}

			$dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);

			$stmt = $dbh->prepare("update Customer set CustomerEmail = ?
				where CustomerID = ?");
			$stmt->bindParam(1, $_POST['email']);
			$stmt->bindParam(2, $_SESSION['CustomerID']);

			if($stmt->execute())
				echo '{"error":"0"}';
			else
				echo '{"error":"2"}';
			
		break;
		case 'change_password':
			if(empty($_POST['password']) || !preg_match(LoginControl::PASSWORD,$_POST['password'])){
				echo '{"error":"1"}';
				break;
			}

			$dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);

			$stmt = $dbh->prepare("update Customer set HashedPassword = ?
				where CustomerID = ?");
			$stmt->bindParam(1, crypt($_POST['password']));
			$stmt->bindParam(2, $_SESSION['CustomerID']);

			if($stmt->execute())
				echo '{"error":"0"}';
			else
				echo '{"error":"2"}';
		break;
		default: break;

	}
}
?>
