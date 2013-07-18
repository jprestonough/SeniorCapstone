<?PHP
include("includes/header.php");

function getOrderID($dbh){
    $stmt = $dbh->prepare("select OrderID from CustomerOrder
		where Customer_CustomerID = ? and OrderStatus_OrderStatusID = 1;");
    $stmt->bindParam(1, $_SESSION['CustomerID']);
    $stmt->execute();

    $data = $stmt->fetchAll();

    if(count($data) == 0){
		$stmt = $dbh->prepare("insert into CustomerOrder (Customer_CustomerID,DateCreated,OrderStatus_OrderStatusID)
			values (?,NOW(),1);");
	    $stmt->bindParam(1, $_SESSION['CustomerID']);
	    $stmt->execute();
		$stmt = $dbh->prepare("select OrderID from CustomerOrder
			where Customer_CustomerID = ? and OrderStatus_OrderStatusID = 1;");
	    $stmt->bindParam(1, $_SESSION['CustomerID']);
	    $stmt->execute();

	    $data = $stmt->fetchAll();
	}
	return $data[0][0];
}

if($loginControl -> checkLogin() && !empty($_POST['function'])){
	switch ($_POST['function']) {
		case 'check_cart':
			$dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);

			$orderID = getOrderID($dbh);
			
			$stmt = $dbh->prepare("select Name,OrderItem.Quantity,Price,ItemID from OrderItem
				join Item on Item_ItemID = ItemID
				where CustomerOrder_OrderID = ?");
		    $stmt->bindParam(1, $orderID);
		    $stmt->execute();

		    $data = $stmt->fetchAll(PDO::FETCH_ASSOC);
		    echo '{"items":'.json_encode($data).'}';
		break;
		case 'modify_cart':
			$dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);
			
			$orderID = getOrderID($dbh);

			$stmt = $dbh->prepare("update OrderItem set Quantity = ?
				where CustomerOrder_OrderID = ? and Item_ItemID = ?");
			$stmt->bindParam(1, $_POST['quantity']);
		    $stmt->bindParam(2, $orderID);
		    $stmt->bindParam(3, $_POST['itemid']);

		    if($stmt->execute())
		    	echo '{"error":"false"}';
		    else
		    	echo '{"error":"true"}';
		break;
		case 'add_cart':
			$dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);
			
			$orderID = getOrderID($dbh);

			$stmt = $dbh->prepare("select Quantity from OrderItem
				where CustomerOrder_OrderID = ? and Item_ItemID = ?");
		    $stmt->bindParam(1, $orderID);
		    $stmt->bindParam(2, $_POST['itemid']);
		    $stmt->execute();

		    $data = $stmt->fetchAll();

	        if(count($data) == 0){
				$stmt = $dbh->prepare("insert into OrderItem (Item_ItemID,CustomerOrder_OrderID,ItemStatus_ItemStatusID,Quantity)
					values (?,?,1,?);");
		    	$stmt->bindParam(1, $_POST['itemid']);
				$stmt->bindParam(2, $orderID);
				$stmt->bindParam(3, $_POST['quantity']);
			} else {
				$stmt = $dbh->prepare("update OrderItem set Quantity = Quantity + ?
					where CustomerOrder_OrderID = ? and Item_ItemID = ?");
				$stmt->bindParam(1, $_POST['quantity']);
			    $stmt->bindParam(2, $orderID);
			    $stmt->bindParam(3, $_POST['itemid']);
			}
		    if($stmt->execute())
		    	echo '{"error":"false"}';
		    else
		    	echo '{"error":"true"}';
		break;
		case 'remove_cart':
			$dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);
			
			$orderID = getOrderID($dbh);

			$stmt = $dbh->prepare("delete from OrderItem 
				where CustomerOrder_OrderID = ? and Item_ItemID = ?");
		    $stmt->bindParam(1, $orderID);
		    $stmt->bindParam(2, $_POST['itemid']);

		    if($stmt->execute())
		    	echo '{"error":"false"}';
		    else
		    	echo '{"error":"true"}';
		break;
		case 'checkout_cart':
			$dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);

			$orderID = getOrderID($dbh);

			$stmt = $dbh->prepare("update CustomerOrder set OrderStatus_OrderStatusID = 2
				where OrderID = ?");
		    $stmt->bindParam(1, $orderID);

		    if($stmt->execute())
		    	echo '{"error":"false"}';
		    else
		    	echo '{"error":"true"}';
		break;
		default:
			# code...
			break;
	}
}
?>
