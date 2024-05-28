<?php
session_start();

// Saioaren aldagai guztiak suntsitzea
$_SESSION = array();

if (ini_get("session.use_cookies")) {
    $params = session_get_cookie_params();
    setcookie(session_name(), '', time() - 42000,
        $params["path"], $params["domain"],
        $params["secure"], $params["httponly"]
    );
}

// Saioa suntsitu
session_destroy();

echo json_encode(['status' => 'success']);
?>
