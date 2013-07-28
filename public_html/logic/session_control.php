<?PHP
if(!isset($_SESSION)){ session_start(); }

if (isset($_SESSION['LAST_ACTIVITY']) && (time() - $_SESSION['LAST_ACTIVITY'] > 1800)) {
	// last request was more than 30 minates ago
	session_destroy();   // destroy session data in storage
	session_start();
	session_regenerate_id(true);
} else {
	$_SESSION['LAST_ACTIVITY'] = time(); // update last activity time stamp
}

if (!isset($_SESSION['CREATED'])) {
	$_SESSION['CREATED'] = time();
} else if (time() - $_SESSION['CREATED'] > 1800) {
	// session started more than 30 minates ago
	session_regenerate_id(true);    // change session ID for the current session an invalidate old session ID
	$_SESSION['CREATED'] = time();  // update creation time
}
?>
