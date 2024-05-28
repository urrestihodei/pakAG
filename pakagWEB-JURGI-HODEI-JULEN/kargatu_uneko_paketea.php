<?php
include "konexioa.php";

// Kontsulta, zein_entregatu = 1 duen paketea lortzeko
$sql = "SELECT * FROM paketea INNER JOIN bezeroa ON paketea.bezeroa_nan = bezeroa.bezeroa_nan WHERE zein_entregatu = 1 LIMIT 1";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
// Paketearen datuak lortu
while($row = $result->fetch_assoc()) {
        $id = $row["ID"];
        $entrega_data = $row["entrega_data"];
        $bezeroa_izena = $row["bezeroa_izena"];
        $bezeroa_helbidea = $row["bezeroa_helbidea"];
        ?>
        <div class='pakete'>
            <p>ID: <?php echo $id; ?></p>
            <p>Entrega Data: <?php echo $entrega_data; ?></p>
            <p>Bezeroa Izena: <?php echo $bezeroa_izena; ?></p>
            <p>Helbidea: <?php echo $bezeroa_helbidea; ?></p>
            <form action='paketea_entregatu.php' method='POST'>
                <textarea name='nota_<?php echo $id; ?>' placeholder='Ohar bat idatzi' rows='6' cols='50'></textarea>
                <br>
                <button type='submit' name='entregado_<?php echo $id; ?>'>Entregatua bezela markatu</button>
            </form>
        </div>
        <?php
    }
} else {
    echo "<p>Ez duzu paketerik aukeratu.</p>";
}

$conn->close();
?>
