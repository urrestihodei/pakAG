<?php
include 'konexioa.php';

$sql = "SELECT * FROM historiala";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    echo "<table class='table'><tr><th>ID</th><th>Data</th><th>Izena</th><th>Produktua</th><th>Oharra</th></tr>";
    // Emaitzei buruzko iterazioa egin eta datu-lerro bakoitza erakutsi
    while ($row = $result->fetch_assoc()) {
        echo "<tr><td>" . $row["ID"] . "</td>
        <td>" . $row["langilea_nan"] . "</td>
        <td>" . $row["bezeroa_nan"] . "</td>
        <td>" . $row["entrega_data"] . "</td>
        <td>" . $row["oharra"] . "</td></tr>";
    }
    echo "</table>";
} else {
    echo "Ez dago emaitzarik";
}
$conn->close();
?>
