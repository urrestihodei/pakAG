<?php
include 'konexioa.php';

if (!isset($_SESSION['username'])) {
    die('Usuario no autenticado');
}

$empleado_dni = $_SESSION['username'];

$sql = "SELECT p.*, b.bezeroa_izena, b.bezeroa_helbidea, b.bezeroa_telefonoa FROM paketea p
        JOIN langilea l ON p.langilea_nan = l.langilea_nan
        JOIN bezeroa b ON p.bezeroa_nan = b.bezeroa_nan 
        WHERE l.erabiltzailea = ?";
$stmt = $conn->prepare($sql);
if ($stmt === false) {
    die('Prepare failed: ' . htmlspecialchars($conn->error));
}
$stmt->bind_param("s", $empleado_dni);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    echo "<table class='table table-striped table-bordered'>
            <thead class='table-dark'>
                <tr>
                    <th>ID</th>
                    <th>Data</th>
                    <th>Helbidea</th>
                    <th>Bezeroa Izena</th>
                    <th>Telefonoa</th>
                    <th>Egoera</th>
                </tr>
            </thead>
            <tbody>";
    while ($row = $result->fetch_assoc()) {
        echo "<tr>
                <td>" . $row['ID'] . "</td>
                <td>" . $row['entrega_data'] . "</td>
                <td>" . $row['bezeroa_helbidea'] . "</td>
                <td>" . $row['bezeroa_izena'] . "</td>
                <td>" . $row['bezeroa_telefonoa'] . "</td>
                <td>";
        if ($row['zein_entregatu'] == 0) {
            echo "<button type='button' class='btn btn-success entregar-btn' data-id='" . $row['ID'] . "'>Entregatu</button>";
        } else {
            echo "<span class='badge bg-success'>Aukeratuta</span>";
        }
        echo "</td>
              </tr>";
    }
    echo "</tbody></table>";
} else {
    echo "<div class='alert alert-warning' role='alert'>Ez dira emaitzak aurkitu.</div>";
}

$stmt->close();
$conn->close();
?>
