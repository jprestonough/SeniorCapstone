<?PHP
//to view this page, please follow this url:
//http://23.21.158.161:4912/item_page.php?pass=yesbaby&id=1

include("includes/header.php");

$go = $_GET["pass"];
$itemID = intval($_GET["id"]);

if ($go == "yesbaby"  && !empty($itemID) && $itemID>0) {
     //qr code generation based on the itemID
    $size          = "300x300";
    $correction    = "L";
    $encoding      = "UTF-8";
      
    $rootUrl = "https://chart.googleapis.com/chart?cht=qr&chs=$size&chl=$itemID&choe=$encoding&chld=$correction";
      
    $qrImg = '<img src="'.$rootUrl.'">';

    //dbstuff
    $dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);
	
    $stmt = $dbh->prepare("select Item.Name,Description,Price,Quantity,Department.Name from Item join Department on Item.Department_DepartmentID = Department.DepartmentID where ItemID = ?;");
    $stmt->bindParam(1, $itemID);
    $stmt->execute();

    $data = $stmt->fetchAll();

    if(count($data) > 0) {
	$prod["name"] = $data[0][0];
	$prod["desc"] = $data[0][1];
	$prod["price"] = $data[0][2];
	$prod["qty"] = $data[0][3];
	$prod["dept"] = $data[0][4];



        $printOut = '<html>
				<head>
					
				<title>Loss Boys - ' . $prod["name"] .'</title>
				<link href="http://fonts.googleapis.com/css?family=Titillium+Web:600,400" rel="stylesheet" type="text/css">
				<style type="text/css">
				
					body {
						background-color: black;	
					}
					
					h1 {
						font-size: 64px;	
						font-weight: 600;
						line-height: 10px;
					}
	
					*{
						text-align: center;
						font-family: \'Titillium Web\',sans-serif;
					}
	
					#itemImage {
						width: 300px;
						background-image: url(\'item_images/' . $itemID . '.jpg\');
						background-size: cover;
						background-repeat: none;
						background-position: center;
						float: left; 
						height: 300px;
						
					}
					
					#itemCode {
						width: 300px;
						height: 300px;
						float: right;
					}
					
					#itemCode h2 {
						margin-top: -30px;	
					}
					
					#itemName {
						width: 600px;
						float: left;
						border-bottom: 5px solid black;
						margin-bottom: 30px;
					}
					
					#container {
						background-color: white;
						width: 600px;
						margin: auto;
						height: 450px;
						padding: 30px;
						margin-top: 50px;
						border-radius: 25px;
							
					}
				</style>
				</head>
		
				<body>
					<div id="container">
						<div id="itemName"><h1>' . substr($prod["name"],0,15) . '</h1></div>
						<div id="itemImage"></div>
						<div id="itemCode">' . $qrImg . '<br/><h2>Scan me for more info!</h2></div>
					</div>
				</body>
     	    </html>';
    } else {
	$printOut = "Error 00";
	}
} else {
	$printOut = "Error 01";
}

echo $printOut;



?>

