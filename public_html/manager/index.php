
<?php
	ini_set('display_errors', '1');
	error_reporting(E_ALL);
	require_once("../includes/vars.php");
	require_once("session_control.php");
	require_once("employeeLogin_control.php");
	
	
	$logged = ($employeeLoginControl -> checkLogin());
	$nav = (isset($_GET["act"])) ? $_GET["act"] : NULL;
	$note = (isset($_GET["note"])) ? $_GET["note"] : NULL;
	$idNum = (isset($_GET["id"])) ? $_GET["id"] : NULL;

	function printLogin($note){
		$res = '<div class="container" id="login">
				  <form class="form-signin" action="login.php" method="post">
				  	
					<h2 class="form-signin-heading">Manager Login</h2>
					
					<input name="employeeid" type="text" class="input-block-level" placeholder="Employee ID" />
					<input name="password" type="password" class="input-block-level" placeholder="Password" />
					<input type="hidden" name="source" value="web" />';
					
		if($note == "fail") { 
		
			$res .= '<div class="alert alert-error">Incorrect Username or Password</div>';
		} 
		$res .= '<button class="btn btn-large btn-primary" type="submit">Sign in</button></form></div>';
		
		return $res;
	}

	function printPage($nav, $DBuser, $DBpass, $idNum) {
		$res = '<div class="container"><div class="navbar" id="nav">
      <div class="navbar-inner">
        <a class="brand" href="http://23.21.158.161:4912/manager/index.php"><img src="weblogo.png" /> Management Portal</a>
        <ul class="nav">
          <li class="divider-vertical"></li>
          <li' . (($nav == "associates" ) ? ' class="active"':'') . '><a href="?act=associates"><i class="icon-user"></i> Associates</a></li>
          <li class="divider-vertical"></li>
          <li' . (($nav == "orders" ) ? ' class="active"':'') . '><a href="?act=orders"><i class="icon-shopping-cart"></i> Orders</a></li>
          <li class="divider-vertical"></li>
          
        </ul>
        
        <ul class="nav pull-right">
          <li class="divider-vertical"></li>
          <li><a href="logout.php?source=web"><i class="icon-off"></i> Logout</a></li>
        </ul>
      </div>
    </div>';
		switch($nav) {
			case "orders":
				$res .= printOrders($DBuser,$DBpass);
				break;
			case "associates":
				$res .= printEmployees($DBuser, $DBpass);
				break;
			case "associate":
				$res .= printAssociate($DBuser,$DBpass,$idNum);
				break;
			case "order":
				$res .=  printOrder($DBuser,$DBpass,$idNum);
				break;
			default:
				$res .= printWelcome();
		}
		$res .= "</div>";
	 	return $res;
	}
	
	function printWelcome() {
		$res = '<div class="well"><h4>Welcome.</h4><div class="alert alert-info">You are logged in as manager #' . $_SESSION['EmployeeID'] . '.</div></div>';
		return $res;
	}
	
	function printAssociate($DBuser, $DBpass, $idNum) {
		$res = '';
		$dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);

		$stmt = $dbh->prepare("select EmployeeID,Title from Employee inner join EmployeeType on EmployeeType.EmployeeTypeID=Employee.EmployeeType_EmployeeTypeID where EmployeeID = ?;");
		$stmt->bindParam(1,$idNum);
		$stmt->execute();
		
		$data = $stmt->fetchAll();
		
		if(count($data) > 0){ 
			//print_r($data);
			$res .= '<a id="delButton" href="#delEmployee" role="button" class="btn" data-toggle="modal"><i class="icon-trash"></i> Delete Associate</a>
			<a id="changeButton" href="#changeEmployee" role="button" class="btn" data-toggle="modal"><i class="icon-lock"></i> Change Password</a>
			<table id="associate" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover">
			<thead><tr>
				<th>Employee ID</th>
				<th>Position</th>
			</tr></thead><tbody>';
			foreach ($data as $row) {
				
				$res .= "<tr>
					<td>0" . $row["EmployeeID"] . "</td>
					<td>" . $row["Title"] . "</td>
				</tr>";
			}
			$res .= '</tbody></table>
			
			</div>';
			
				
		} else {
			$res = "error";
		}
		return $res;
	}
	
	function printEmployees($DBuser, $DBpass) {
		$res = '';
		$dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);

		$stmt = $dbh->prepare("select EmployeeID,Title from Employee inner join EmployeeType on EmployeeType.EmployeeTypeID=Employee.EmployeeType_EmployeeTypeID;");
		$stmt->execute();
		
		$data = $stmt->fetchAll();
		
		if(count($data) > 0){ 
			//print_r($data);
			$res .= '<div class="container">
			
			<!-- Button to trigger modal: <a id="addButton" href="#addEmployee" role="button" class="btn" data-toggle="modal"><i class="icon-plus"></i> Add New Associate</a>-->

<!-- Modal -->
<div id="addEmployee" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">x</button>
    <h3>Add New Associate</h3>
  </div>
  <div class="modal-body">
    
	
	<form id="createNewAssociate" method="post" class="form-horizontal">
	<input type="hidden" name="createEmployee" value="new" />
	<input type="hidden" name="source" value="web" />
  <div class="control-group">
    <label class="control-label" for="inputPosition">Position</label>
    <div class="controls">
		  <select name="type">
			  <option value="1">Inventory</option>
			  <option value="2">Manager</option>
			  <option value="3">Pickup</option>
		</select>
    </div>
  </div>
  <div class="control-group">
    <label class="control-label" for="password">Password</label>
    <div class="controls">
      <input type="password" name="password" id="password" placeholder="Choose Password">
    </div>
  </div>
  <div class="alert alert-error hide" id="createMsg">
  </div>
  
</form>
	

  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
	<button class="btn btn-primary" id="createNewAssociateSubmit">Add</button>
    
  </div>
</div>
			
			
			
			
			<table id="associates" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover">
			<thead><tr>
				<th>Employee ID</th>
				<th>Position</th>
			</tr></thead><tbody>';
			foreach ($data as $row) {
				
				$res .= "<tr>
					<td>0" . $row["EmployeeID"] . "</td>
					<td>" . $row["Title"] . "</td>
				</tr>";
			}
			$res .= '</tbody></table>
			
			</div>';
			
				
		} else {
			$res = "error";
		}
		return $res;
	}
	
	
	function printOrder($DBuser, $DBpass, $idNum){
		
		$res = '';
		if(preg_match("/[0-9]+/",$idNum)) {
		
			$dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);
			
			$stmt = $dbh->prepare("select OrderID, DateCreated, CustomerFirst, CustomerEmail, CustomerLast, Description from CustomerOrder inner join OrderStatus on CustomerOrder.OrderStatus_OrderStatusID=OrderStatus.OrderStatusID inner join Customer on CustomerOrder.Customer_CustomerID = Customer.CustomerID where OrderID = ?;");
			$stmt->bindParam(1, $idNum);
			$stmt->execute();
			
			$datax = $stmt->fetchAll();
			
		
			$stmt = $dbh->prepare("select OrderItem.Item_ItemID, Item.Name, OrderItem.Quantity, ItemStatus.Description from OrderItem inner join Item on OrderItem.Item_ItemID=Item.ItemID inner join ItemStatus on OrderItem.ItemStatus_ItemStatusID = ItemStatus.ItemStatusID where OrderItem.CustomerOrder_OrderID = ? AND OrderItem.Quantity != 0;");
			$stmt->bindParam(1, $idNum);
			$stmt->execute();
			
			$data = $stmt->fetchAll();
			
			if(count($datax) > 0){
				$res .= '<div class="container"><table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover">
				<thead><tr>
					<th>OrderID</th>
					<th>Customer</th>
					<th>Status</th>
					<th>Timestamp</th>
					
				</tr></thead><tbody>';
				foreach ($datax as $rowx) {
					
					$res .= "<tr>
						<td>" . $rowx["OrderID"] . "</td>
						<td>" . $rowx["CustomerLast"] . ", " . $rowx["CustomerFirst"] . " (" . $rowx["CustomerEmail"] . ") " . "</td>
						<td>" . $rowx["Description"] .  "</td>
						<td>" . $rowx["DateCreated"] . "</td>
					</tr>";
				}
				
				$res .= "</tbody></table></div>";			
			}
			
			if(count($data) > 0){ 
				//print_r($data);
				$res .= '<div class="container"><table id="items" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover">
				<thead><tr>
					<th>Item ID</th>
					<th>Name</th>
					<th>Qty</th>
					<th>Status</th>
					
				</tr></thead><tbody>';
				foreach ($data as $row) {
					
					$res .= "<tr>
						<td>0" . $row["Item_ItemID"] . "</td>
						<td>" . $row["Name"] . "</td>
						<td>" . $row["Quantity"] .  "</td>
						<td>" . $row["Description"] . "</td>
					</tr>";
				}
				$res .= "</tbody></table></div>";
				
				
			} else {
				$res = '<div class="well">No Items Found for this Order</div>';
			}
		}
		return $res;
	}
	
	function printOrders($DBuser, $DBpass) {
		$res = '';
		$dbh = new PDO('mysql:host=localhost;dbname=capstone', $DBuser, $DBpass);

		$stmt = $dbh->prepare("select OrderID, DateCreated, CustomerFirst, CustomerEmail, CustomerLast, Description from CustomerOrder inner join OrderStatus on CustomerOrder.OrderStatus_OrderStatusID=OrderStatus.OrderStatusID inner join Customer on CustomerOrder.Customer_CustomerID = Customer.CustomerID;");
		
		$stmt->execute();
		
		$data = $stmt->fetchAll();
		
		
		if(count($data) > 0){ 
			//print_r($data);
			$res .= '<div class="container"><table id="orders" cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover">
			<thead><tr>
				<th>Order ID</th>
				<th>Customer</th>
				<th>Status</th>
				<th>Timestamp</th>
				
			</tr></thead><tbody>';
			foreach ($data as $row) {
				
			 	$res .= "<tr>
					<td>0" . $row["OrderID"] . "</td>
					<td>" . $row["CustomerLast"] . ", " . $row["CustomerFirst"] . " (" . $row["CustomerEmail"] . ") " . "</td>
					<td>" . $row["Description"] .  "</td>
					<td>" . $row["DateCreated"] . "</td>
				</tr>";
			}
			$res .= "</tbody></table></div>";
			
			
		} else {
			$res = "error";
		}
		return $res;
	}
?>

<!DOCTYPE html>
<html>
  <head>
    <title>LossBoys Management Portal <?php 
	if(isset($nav)){
		switch($nav) {
			case "associates":
				echo " - Associates";
				break;
			case "orders":
				echo " - Orders";
				break;
			case "associate":
				echo " - Associate #" . (isset($idNum)?$idNum:'');
				break;
			case "order":
				echo " - Order #" . (isset($idNum)?$idNum:'');
				break;
		}
		
	}
	
	
	?></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link rel="icon" type="image/png" href="http://23.21.158.161:4912/manager/icon.png" />
	
    <!-- Bootstrap -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen" />
    <link href="DT_bootstrap.css" rel="stylesheet" media="screen" />
        <style type="text/css">
      <?php if(!$logged) { echo 'body {
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }
	  #login {
		padding-top: 50px;  
	  }'; } else {
		  echo '
		  
		  #nav {
			margin-bottom: 10px; 
			margin-top: 10px;
		  }
		  
		  #addButton {
			  margin-left: 20px;
			margin-top: -3px;  
		  }
		  
		  #delButton, #changeButton {
			  margin-bottom: 10px;
		  }
		  
		  ';
	  }?>

    </style>
  </head>
  <body>
  	<div id="test"></div>
    <?php echo ($logged) ? printPage($nav, $DBuser, $DBpass, $idNum) : printLogin($note); ?> 
    
	
        
        
    
    
    
    
    
    
    <script type="text/javascript" language="javascript" src="js/jquery.js"></script>
    <script type="text/javascript" language="javascript" src="js/jquery.dataTables.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>

    <script type="text/javascript" charset="utf-8">
	

			/* Default class modification */
			$.extend( $.fn.dataTableExt.oStdClasses, {
				"sWrapper": "dataTables_wrapper form-inline",
				"sSortAsc": "header headerSortDown",
				"sSortDesc": "header headerSortUp",
				"sSortable": "header"
			} );

			/* API method to get paging information */
			$.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
			{
				return {
					"iStart":         oSettings._iDisplayStart,
					"iEnd":           oSettings.fnDisplayEnd(),
					"iLength":        oSettings._iDisplayLength,
					"iTotal":         oSettings.fnRecordsTotal(),
					"iFilteredTotal": oSettings.fnRecordsDisplay(),
					"iPage":          Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
					"iTotalPages":    Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
				};
			}

			/* Bootstrap style pagination control */
			$.extend( $.fn.dataTableExt.oPagination, {
				"bootstrap": {
					"fnInit": function( oSettings, nPaging, fnDraw ) {
						var oLang = oSettings.oLanguage.oPaginate;
						var fnClickHandler = function ( e ) {
							e.preventDefault();
							if ( oSettings.oApi._fnPageChange(oSettings, e.data.action) ) {
								fnDraw( oSettings );
							}
						};

						$(nPaging).addClass('pagination').append(
							'<ul>'+
								'<li class="prev disabled"><a href="#">&larr; '+oLang.sPrevious+'</a></li>'+
								'<li class="next disabled"><a href="#">'+oLang.sNext+' &rarr; </a></li>'+
							'</ul>'
						);
						var els = $('a', nPaging);
						$(els[0]).bind( 'click.DT', { action: "previous" }, fnClickHandler );
						$(els[1]).bind( 'click.DT', { action: "next" }, fnClickHandler );
					},

					"fnUpdate": function ( oSettings, fnDraw ) {
						var iListLength = 5;
						var oPaging = oSettings.oInstance.fnPagingInfo();
						var an = oSettings.aanFeatures.p;
						var i, j, sClass, iStart, iEnd, iHalf=Math.floor(iListLength/2);

						if ( oPaging.iTotalPages < iListLength) {
							iStart = 1;
							iEnd = oPaging.iTotalPages;
						}
						else if ( oPaging.iPage <= iHalf ) {
							iStart = 1;
							iEnd = iListLength;
						} else if ( oPaging.iPage >= (oPaging.iTotalPages-iHalf) ) {
							iStart = oPaging.iTotalPages - iListLength + 1;
							iEnd = oPaging.iTotalPages;
						} else {
							iStart = oPaging.iPage - iHalf + 1;
							iEnd = iStart + iListLength - 1;
						}

						for ( i=0, iLen=an.length ; i<iLen ; i++ ) {
							// Remove the middle elements
							$('li:gt(0)', an[i]).filter(':not(:last)').remove();

							// Add the new list items and their event handlers
							for ( j=iStart ; j<=iEnd ; j++ ) {
								sClass = (j==oPaging.iPage+1) ? 'class="active"' : '';
								$('<li '+sClass+'><a href="#">'+j+'</a></li>')
									.insertBefore( $('li:last', an[i])[0] )
									.bind('click', function (e) {
										e.preventDefault();
										oSettings._iDisplayStart = (parseInt($('a', this).text(),10)-1) * oPaging.iLength;
										fnDraw( oSettings );
									} );
							}

							// Add / remove disabled classes from the static elements
							if ( oPaging.iPage === 0 ) {
								$('li:first', an[i]).addClass('disabled');
							} else {
								$('li:first', an[i]).removeClass('disabled');
							}

							if ( oPaging.iPage === oPaging.iTotalPages-1 || oPaging.iTotalPages === 0 ) {
								$('li:last', an[i]).addClass('disabled');
							} else {
								$('li:last', an[i]).removeClass('disabled');
							}
							
							
						}
						
						attachListeners();
			
					}
				}
			} );

			/* Table initialisation */
			$(document).ready(function() {
				$('#orders').dataTable({
					"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
					"sPaginationType": "bootstrap",
					"oLanguage": {
						"sLengthMenu": "_MENU_ records per page"
					}
				});
				
				$('#associates').dataTable({
					"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
					"sPaginationType": "bootstrap",
					
					"oLanguage": {
						"sLengthMenu": "_MENU_ records per page"
					}
				});
				
				$('#items').dataTable({
					"bPaginate": false,
					"sDom": "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
					"oLanguage": {
						"sLengthMenu": "_MENU_ records per page"
					}
				});
				
				$('#associates_wrapper').find(".span6:first").append('<a id="addButton" href="#addEmployee" role="button" class="btn" data-toggle="modal"><i class="icon-plus"></i> Add New Associate</a>');
				
				attachListeners();
				
				
				$('#createNewAssociateSubmit').click(function(e){
					  e.preventDefault();
					  
					  
					  $.ajax({
						  type: "POST",
						  url: "createAssociate.php",
						  data: $("#createNewAssociate").serialize()
						}).done(function( msg ) {
						  
						  $('#createMsg').html("message is:" + msg);
						  $('#createMsg').show();
						  var obj = jQuery.parseJSON(msg);
						  //$('#createMsg').html(msg);
						  //$('#createMsg').show();
						  if(obj.success == "true" ) {
							  var types = [0,'inventory','manager','pickup'];
							  //$('#createMsg').html('');
							  //$('#createMsg').hide();
							  var position = types[obj.type];
							  var id = "0" + obj.id;
							  $('#associates').dataTable().fnAddData( [
								id,
								position], true
							  );
							  
							  $('#createMsg').hide();
							  $('#addEmployee').modal('hide');
							  $('#password').val('');
							  
						  } else {
							 if(obj.reason == "password") {
								 $('#createMsg').html('The password must be at least 8 characters long.');
								 $('#createMsg').show();
								 $('#addEmployee').modal('show');
							 }
						  }
						});
					  
					  //alert($('#test').html("clicked"));
					  /*
					  $.post('login.php', 
						 $('#createNewAssociate').serialize(), 
						 function(data, status, xhr){
						   // do something here with response;
						 });
					  */
				});
				
			});
			
			function attachListeners() {
				$("#associates tbody tr").click( function( e ) {
					var idNum = parseInt($(this).children(":first").html());
					window.location = "index.php?act=associate&id=" + idNum;
				});
				
				$("#orders tbody tr").click( function( e ) {
					var idNum = parseInt($(this).children(":first").html());
					window.location = "index.php?act=order&id=" + idNum;
				});	
			}
		</script>
  </body>
</html>
		
	
