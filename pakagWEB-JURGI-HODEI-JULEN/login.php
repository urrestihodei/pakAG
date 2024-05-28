<?php
include 'konexioa.php';
session_start();

if (isset($_POST['login'])) {
    $username = $_POST['username'];
    $password = $_POST['password'];

    function validate_credentials($username, $password, $conn) {
        $stmt = $conn->prepare("SELECT * FROM langilea WHERE erabiltzailea = ? AND pasahitza = ?");
        $stmt->bind_param("ss", $username, $password);
        $stmt->execute();
        $result = $stmt->get_result();
        return $result->num_rows > 0;
    }

    if (validate_credentials($username, $password, $conn)) {
        $_SESSION['username'] = $username;
        header("Location: kudeatu.php");
        exit;
    } else {
        echo "Login failed. Invalid username or password.";
    }
}
?>
