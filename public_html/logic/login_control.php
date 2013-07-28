<?PHP
require_once('includes/class.phpmailer.php');
define('GUSER', 'thelossboys@gmail.com'); // GMail username
define('GPWD', 'TheLossBoys@gmail'); // GMail password
class LoginControl{
	
/*
       // Tests the text against the regex test and returns true or false
    public static function test($test, $text)
    {
        $res = false;
        $res = ( preg_match($test, $text) == 1 );
        return $res;
    }
*/	

	const PASSWORD = "/.{8,32}/";
	const EMAIL = "/[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}/";
	const NAME = "/[a-zA-Z]{1,20}/";


	var $DBuser,$DBpass;
	
	function __construct($DBuser,$DBpass){
		$this->DBuser = $DBuser;
		$this->DBpass = $DBpass;
	}

	function login(){
		if(empty($_POST['email']))
			return false;

		if(empty($_POST['password']))
			return false;

		$email = $_POST['email'];
		$password = $_POST['password'];

		if(!isset($_SESSION)){ session_start(); }

		if(!$this->checkUserInDB(strtolower($email),$password))
			return false;
		else
			return true;
	}
	
	function checkUserInDB($email, $password){
		$dbh = new PDO('mysql:host=localhost;dbname=capstone', $this->DBuser, $this->DBpass);

		$stmt = $dbh->prepare("select HashedPassword,CustomerID from Customer where CustomerEmail = ? and Confirmed = 1;");
		$stmt->bindParam(1, $email);
		$stmt->execute();
		
		$data = $stmt->fetchAll();
		if(count($data) > 0 && crypt($password,$data[0][0]) == $data[0][0]){ //Valid login
			$_SESSION['CustomerID'] = $data[0][1];
			return true;
		} else {
			unset($_SESSION['CustomerID']);
			return false;
		}
	}

	function confirmUser(){
		if(empty($_GET['code']))
			return false;

		$dbh = new PDO('mysql:host=localhost;dbname=capstone', $this->DBuser, $this->DBpass);

		$stmt = $dbh->prepare("select CustomerFirst from Customer where ConfirmationCode = ?;");
		$stmt->bindParam(1, $_GET['code']);
		$stmt->execute();
		
		$data = $stmt->fetchAll();
		if(count($data) > 0){ //Valid code
			$stmt = $dbh->prepare("update Customer set Confirmed = 1 where ConfirmationCode = ?;");
			$stmt->bindParam(1, $_GET['code']);
			$stmt->execute();
			return true;
		}else
			return false;
	}

	function create_acct(){
		if(empty($_POST['email'])){
		    return "1";
		} else if(!preg_match(LoginControl::EMAIL,$_POST['email'])){
		    return "bad_email";
		}

		if(empty($_POST['password'])){
		    return "1";
		} else if(!preg_match(LoginControl::PASSWORD,$_POST['password'])){
		    return "bad_password";
		}

		if(empty($_POST['first'])){
		    return "1";
		} else if(!preg_match(LoginControl::NAME,$_POST['first'])){
		    return "bad_first";
		}

		if(empty($_POST['last'])){
		    return "1";
		} else if(!preg_match(LoginControl::NAME,$_POST['first'])){
		    return "bad_last";
		}

		$dbh = new PDO('mysql:host=localhost;dbname=capstone', $this->DBuser, $this->DBpass);

		$stmt = $dbh->prepare("select CustomerEmail from Customer where CustomerEmail = ?;");
		$stmt->bindParam(1, $_POST['email']);
		$stmt->execute();

		$data = $stmt->fetchAll();

		if(count($data) > 0){
		    return "2";
		}
		
		$confirm_code=md5(uniqid(rand())); 

		$stmt = $dbh->prepare("insert into Customer (CustomerFirst,CustomerLast,CustomerEmail,HashedPassword,ConfirmationCode) values (?,?,?,?,?);");
		$stmt->bindParam(1, $_POST['first']);
		$stmt->bindParam(2, $_POST['last']);
		$stmt->bindParam(3, $_POST['email']);
		$stmt->bindParam(4, crypt($_POST['password']));
		$stmt->bindParam(5, $confirm_code);
		$stmt->execute();
		
		$message = "Please click the following link to confirm your email address. http://23.21.158.161:4912/confirm.php?code={$confirm_code}";

		smtpmailer($_POST['email'], 'thelossboys@gmail.com', 'Loss', 'Please confirm email', $message);

		return "0";
	}
	


	function checkLogin(){
		if(empty($_SESSION['CustomerID']))
			return false;
		else
			return true;
    }
	
	function logout(){
		unset($_SESSION['CustomerID']);
	}
}

	function smtpmailer($to, $from, $from_name, $subject, $body) { 
		global $error;
		$mail = new PHPMailer();  // create a new object
		$mail->IsSMTP(); // enable SMTP
		$mail->SMTPDebug = 0;  // debugging: 1 = errors and messages, 2 = messages only
		$mail->SMTPAuth = true;  // authentication enabled
		$mail->SMTPSecure = 'ssl'; // secure transfer enabled REQUIRED for GMail
		$mail->Host = 'smtp.gmail.com';
		$mail->Port = 465; 
		$mail->Username = GUSER;  
		$mail->Password = GPWD;           
		$mail->SetFrom($from, $from_name);
		$mail->Subject = $subject;
		$mail->Body = $body;
		$mail->AddAddress($to);
		if(!$mail->Send()) {
			$error = 'Mail error: '.$mail->ErrorInfo; 
			return false;
		} else {
			$error = 'Message sent!';
			return true;
		}
	}

$loginControl = new LoginControl($DBuser,$DBpass);
?>
