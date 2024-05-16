<?php
include 'konexioa.php';

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

// Obtener el DNI del empleado actualmente autenticado
$empleado_dni = $_SESSION['username'];

// Consulta SQL para seleccionar los datos de la tabla deseada asociados con el DNI del empleado
$sql = "SELECT * FROM paketea p
                JOIN langilea l ON p.langilea_nan = l.langilea_nan
                JOIN bezeroa b ON p.bezeroa_nan = b.bezeroa_nan 
                WHERE erabiltzailea = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $empleado_dni);
$stmt->execute();
$result = $stmt->get_result();

// Si hay al menos una fila de resultados
if ($result->num_rows > 0) {
    // Mostrar los datos en una tabla HTML
    echo "<form method='post'>";
    echo "<table><tr><th>ID</th><th>Data</th><th>Izena</th><th>Produktua</th><th>Egoera</th><th>Acción</th></tr>";
    // Iterar sobre los resultados y mostrar cada fila de datos
    while ($row = $result->fetch_assoc()) {
        echo "<tr><td>" . $row["ID"] . "</td>
        <td>" . $row["entrega_data"] . "</td>
        <td>" . $row["langilea_nan"] . "</td>
        <td>" . $row["bezeroa_nan"] . "</td>
        <td>";
        // Mostrar el estado directamente
        echo ($row["entregatuta"] == 1) ? "Entregado" : "Pendiente";
        echo "</td><td><button type='submit' name='entregado_" . $row["ID"] . "'>Entregado</button></td></tr>";
    }
    echo "</table>";
    echo "</form>";
} else {
    echo "No hay paquetes asociados con el DNI del empleado.";
}
?>
