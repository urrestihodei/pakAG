<?php
include 'konexioa.php';

// Si se envió el formulario
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Verificar si se ha hecho clic en algún botón de entrega
    foreach ($_POST as $key => $value) {
        if (strpos($key, 'entregado_') !== false) {
            // Obtener el ID del pedido desde el nombre del botón
            $pedido_id = substr($key, strlen('entregado_'));
            $nota = $_POST['nota_' . $pedido_id]; // Obtener la nota correspondiente
            
            // Actualizar el valor de la columna "entregatuta" en la base de datos para el registro correspondiente
            $sql_update = "UPDATE paketea SET entregatuta = 1 WHERE ID = ?";
            $stmt_update = $conn->prepare($sql_update);
            if ($stmt_update === false) {
                die('Prepare failed: ' . htmlspecialchars($conn->error));
            }
            $stmt_update->bind_param("i", $pedido_id); // 'i' indica que el parámetro es un entero
            if (!$stmt_update->execute()) {
                die('Execute failed: ' . htmlspecialchars($stmt_update->error));
            }
            $stmt_update->close();
            
            // Actualizar la nota en la tabla historiala después de la actualización
            $sql_update_historiala = "UPDATE historiala SET oharra = ? WHERE ID = ?";
            $stmt_update_historiala = $conn->prepare($sql_update_historiala);
            if ($stmt_update_historiala === false) {
                die('Prepare failed: ' . htmlspecialchars($conn->error));
            }
            $stmt_update_historiala->bind_param("si", $nota, $pedido_id); // 's' indica que el primer parámetro es una cadena, 'i' indica que el segundo es un entero
            if (!$stmt_update_historiala->execute()) {
                die('Execute failed: ' . htmlspecialchars($stmt_update_historiala->error));
            }
            $stmt_update_historiala->close();

            // Eliminar las filas donde el paquete está marcado como entregado
            $sql_delete = "DELETE FROM paketea WHERE entregatuta = 1";
            if (!$conn->query($sql_delete)) {
                die('Error en la eliminación: ' . htmlspecialchars($conn->error));
            }
        }
    }
}

$conn->close();

// Redirigir de vuelta a la página principal después de la operación
header("Location: kudeatu.php");
exit();
?>
