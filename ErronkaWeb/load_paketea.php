<?php
include 'konexioa.php';

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
    echo "<form method='post' id='paketeaForm'>";
    echo "<table class='table'><tr><th>ID</th><th>Data</th><th>Izena</th><th>Produktua</th><th>Egoera</th><th>Acción</th></tr>";
    // Iterar sobre los resultados y mostrar cada fila de datos
    while ($row = $result->fetch_assoc()) {
        echo "<tr><td>" . $row["ID"] . "</td>
        <td>" . $row["entrega_data"] . "</td>
        <td>" . $row["langilea_nan"] . "</td>
        <td>" . $row["bezeroa_nan"] . "</td>
        <td>";
        // Mostrar el estado directamente
        echo ($row["entregatuta"] == 1) ? "Entregado" : "Pendiente";
        echo "</td><td><button type='button' class='entregadoButton' data-id='" . $row["ID"] . "'>Entregado</button></td></tr>";
    }
    echo "</table>";
    echo "</form>";
} else {
    echo "Ez dago langilearen NANarekin lotutako paketerik.";
}

$stmt->close();
$conn->close();
?>
