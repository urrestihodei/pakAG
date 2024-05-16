<?php
include 'konexioa.php';

// Verificar si el usuario ha iniciado sesión
if(isset($_SESSION['username'])) {
    // Obtener nombre y apellido del usuario
    $username = $_SESSION['username'];
    $sql = "SELECT langilea_izena, langilea_abizena FROM langilea WHERE erabiltzailea = ?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("s", $username);
    $stmt->execute();
    $stmt->store_result();
    
    // Verificar si se encontró un usuario con el nombre de usuario actual
    if($stmt->num_rows == 1) {
        $stmt->bind_result($izena, $abizena);
        $stmt->fetch();
        $mensaje_bienvenida = "Bienvenido, $izena $abizena";
    } else {
        // Si no se encontró un usuario con el nombre de usuario actual, redirigir a la página de inicio de sesión
        header("Location: login.php");
        exit();
    }
    $stmt->close();
} else {
    // Si el usuario no ha iniciado sesión, redirigirlo a la página de inicio de sesión
    header("Location: login.php");
    exit();
}
?>

<!DOCTYPE html>
<html lang="es">
<body>
<div>
    <p><?php echo $mensaje_bienvenida; ?></p>
    <div>
       <a href="paketeak.php"><button type="button">PAKETEAK</button></a>
       <a href="historiala.php"><button type="button">HISTORIALA</button></a>
   </div>
</div>
</body>
</html>
