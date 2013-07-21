<?PHP
include("includes/header.php");

function getEmail($dbh,$oid){
    $stmt = $dbh->prepare("select CustomerEmail from Customer join CustomerOrder on Customer_CustomerID = CustomerID
		where OrderID = ?;");
    $stmt->bindParam(1, $oid);
    $stmt->execute();

    $data = $stmt->fetchAll();

    return $data[0][0];
}

if(!empty($_POST['function'])){
	switch ($_POST['function']) {
		case 'check_orders':
			$dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);

			$stmt = $dbh->prepare("select OrderID,count(Item_ItemID) as Items from CustomerOrder 
				left join Customer on CustomerID = Customer_CustomerID
				join OrderItem on OrderID = OrderItem.CustomerOrder_OrderID
				where OrderStatus_OrderStatusID = 2
				and ItemStatus_ItemStatusID = 1
				and Quantity <> 0 group by OrderID;");
		    $stmt->execute();

		    $data = $stmt->fetchAll(PDO::FETCH_ASSOC);
		    echo '{"orders":'.json_encode($data).'}';
		break;
		case 'check_items':
                        $dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);

                        $stmt = $dbh->prepare("select Name,OrderItem.Quantity,Price,ItemID from OrderItem
                                join Item on Item_ItemID = ItemID
                                where CustomerOrder_OrderID = ?
				and OrderItem.ItemStatus_ItemStatusID = 1 and OrderItem.Quantity <> 0");
                    $stmt->bindParam(1, $_POST['orderid']);
                    $stmt->execute();

                    $data = $stmt->fetchAll(PDO::FETCH_ASSOC);
                    echo '{"items":'.json_encode($data).'}';
		break;
		case 'pick_item':
			$dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);

			$stmt = $dbh->prepare("select Quantity from OrderItem
				where CustomerOrder_OrderID = ? and Item_ItemID = ?
				and ItemStatus_ItemStatusID = 1");
			    $stmt->bindParam(1, $_POST['orderid']);
			    $stmt->bindParam(2, $_POST['itemid']);
			    $stmt->execute();

			    $data = $stmt->fetchAll();

			if(count($data) == 0){
				echo '{"error":"invalid"}';
				return;
			}

			$stmt = $dbh->prepare("select Quantity from OrderItem
				where CustomerOrder_OrderID = ? and Item_ItemID = ?
				and ItemStatus_ItemStatusID = 1");
			    $stmt->bindParam(1, $_POST['orderid']);
			    $stmt->bindParam(2, $_POST['itemid']);
			    $stmt->execute();

			    $data = $stmt->fetchAll();

			if($data[0][0] == '0'){
				echo '{"error":"done"}';
				return;
			}

			$stmt = $dbh->prepare("select Quantity from OrderItem
				where CustomerOrder_OrderID = ? and Item_ItemID = ?
				and ItemStatus_ItemStatusID = 3");
			    $stmt->bindParam(1, $_POST['orderid']);
			    $stmt->bindParam(2, $_POST['itemid']);
			    $stmt->execute();

			    $data = $stmt->fetchAll();

			if(count($data) == 0){
				$stmt = $dbh->prepare("insert into OrderItem (Item_ItemID,CustomerOrder_OrderID,ItemStatus_ItemStatusID,Quantity)
					values (?,?,3,1);");
				$stmt->bindParam(1, $_POST['itemid']);
				$stmt->bindParam(2, $_POST['orderid']);
			} else {
				$stmt = $dbh->prepare("update OrderItem set Quantity = Quantity + 1
					where CustomerOrder_OrderID = ? and Item_ItemID = ?
					and ItemStatus_ItemStatusID = 3");
			        $stmt->bindParam(1, $_POST['orderid']);
			        $stmt->bindParam(2, $_POST['itemid']);
			}

		    if(!$stmt->execute())
		    	echo '{"error":"true"}';

		   $stmt = $dbh->prepare("update OrderItem set Quantity = Quantity - 1
			where CustomerOrder_OrderID = ? and Item_ItemID = ?
			and ItemStatus_ItemStatusID = 1");
		   $stmt->bindParam(1, $_POST['orderid']);
		   $stmt->bindParam(2, $_POST['itemid']);

		    if($stmt->execute())
		    	echo '{"error":"false"}';

			$stmt = $dbh->prepare("select Quantity from OrderItem
				where CustomerOrder_OrderID = ? 
				and ItemStatus_ItemStatusID = 1 and Quantity <> 0");
			    $stmt->bindParam(1, $_POST['orderid']);
			    $stmt->execute();

			    $data = $stmt->fetchAll();

			if(count($data) == 0){
				echo '{"error":"orderdone"}';
			   $message = "Your order has been completed. Please pick it up at the checkout counter.";

			   smtpmailer(getEmail($dbh,$_POST['orderid']), 'thelossboys@gmail.com', 'Loss', 'Order Completed', $message);
			}

		break;
		default:
			# code...
			break;
	}
}
?>
