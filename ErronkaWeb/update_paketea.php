<?php
include 'konexioa.php';
session_start(); // Asegúrate de iniciar la sesión

// Si se envió el formulario
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Verificar si se ha hecho clic en algún botón de entrega
    foreach ($_POST as $key => $value) {
        if (strpos($key, 'entregado_') !== false) {
            // Obtener el ID del pedido desde el nombre del botón
            $pedido_id = substr($key, strlen('entregado_'));
            
            // Actualizar el valor de la columna "Egoera" en la base de datos para el registro correspondiente
            $sql_update = "UPDATE paketea SET entregatuta = 1 WHERE ID = ?";
            $stmt = $conn->prepare($sql_update);
            $stmt->bind_param("i", $pedido_id); // 'i' indica que el parámetro es un entero
            $stmt->execute();
            $stmt->close();
        }
    }

    // Eliminar las filas donde el paquete está marcado como entregado
    $sql_delete = "DELETE FROM paketea WHERE entregatuta = 1";
    $conn->query($sql_delete);
}

$conn->close();
?>

