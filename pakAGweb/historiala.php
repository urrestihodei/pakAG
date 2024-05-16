<h2>HISTORIALA</h2>
<?php
    include 'konexioa.php';
    // Consulta SQL para seleccionar los datos de la tabla deseada
    $sql = "SELECT * FROM historiala";
    $result = $conn->query($sql);

    // Si hay al menos una fila de resultados
    if ($result->num_rows > 0) {
        // Mostrar los datos en una tabla HTML
        echo "<table><tr><th>ID</th><th>Data</th><th>Izena</th><th>Produktua</th></tr>";
        // Iterar sobre los resultados y mostrar cada fila de datos
        while ($row = $result->fetch_assoc()) {
            echo "<tr><td>" . $row["ID"] . "</td>
            <td>" . $row["langilea_nan"] . "</td>
            <td>" . $row["bezeroa_nan"] . "</td>
            <td>" . $row["entrega_data"] . "</td>
            <td>" . $row["oharra"] . "</td></tr>";
        }
        echo "</table>";
    } else {
        echo "0 resultados";
    }
    $conn->close();
?>