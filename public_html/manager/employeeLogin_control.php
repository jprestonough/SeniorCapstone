<?PHP

class EmployeeLoginControl{
	

	const PASSWORD = "/.{8,32}/";
	const EID = "/[0-9]+/";
	const USERNAME = "/[a-zA-Z0-9]{3,16}/";

	var $DBuser,$DBpass;
	
	function __construct($DBuser,$DBpass){
		$this->DBuser = $DBuser;
		$this->DBpass = $DBpass;
	}

	function login($type){
		
		if(empty($_POST['employeeid']) || !preg_match(EmployeeLoginControl::EID,$_POST['employeeid']))
			return false;

		if(empty($_POST['password']) || !preg_match(EmployeeLoginControl::PASSWORD,$_POST['password']))
			return false;

		$eid = $_POST['employeeid'];
		$password = $_POST['password'];

		if(!isset($_SESSION)){ session_start(); }
		if(!$this->checkEmployeeInDB($eid,$password,$type))
			return false;
		else
			return true;
	}
	
	function checkEmployeeInDB($eid, $password, $type){
		
		$dbh = new PDO('mysql:host=localhost;dbname=capstone', $this->DBuser, $this->DBpass);

		$stmt = $dbh->prepare("select HashedPassword,EmployeeID,EmployeeType_EmployeeTypeID from Employee where EmployeeID = ?;");
		$stmt->bindParam(1, $eid);
		$stmt->execute();
		
		$data = $stmt->fetchAll();
		if(count($data) > 0 && crypt($password,$data[0][0]) == $data[0][0] && (!isset($type) || (isset($type) && $type == $data[0][2]))){ //Valid login
			$_SESSION['EmployeeID'] = $data[0][1];
			$_SESSION['EmployeeTitle'] = $data[0][2];
			
			return true;
			
			
		} else {
			unset($_SESSION['EmployeeID']);
			return false;
		}
	}


	function create_acct($type,$password){
		
		if(!isset($type)) {
			return  '{"success":"false", "reason":"type"}';
		} 
		
		if(!isset($password) || !preg_match(EmployeeLoginControl::PASSWORD,$password)) {
			return '{"success":"false", "reason":"password"}';
		}
		
		$dbh = new PDO('mysql:host=localhost;dbname=capstone', $this->DBuser, $this->DBpass);
		
		$pass =  crypt($password);

		$stmt = $dbh->prepare("insert into Employee (EmployeeType_EmployeeTypeID, HashedPassword) values (?,?);");
		$stmt->bindParam(1, $type);
		$stmt->bindParam(2, $pass);
		$stmt->execute();
		
		$id = $dbh->lastInsertId();

		return  '{"success":"true","type":"'.$type.'","id":"'.$id.'"}';
	}
	
	function checkType(){
		if(empty($_SESSION['EmployeeTitle'])){
			return false;
		} else {
			return $_SESSION['EmployeeTitle'];	
		}
	}


	function checkLogin(){
		if(empty($_SESSION['EmployeeID']))
			return false;
		else
			return true;
    }
	
	function logout(){
		unset($_SESSION['EmployeeID']);
		unset($_SESSION['EmployeeTitle']);
	}
	/*		case 'change_password':
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
		default: break;*/
}


$employeeLoginControl = new EmployeeLoginControl($DBuser,$DBpass);
?>
