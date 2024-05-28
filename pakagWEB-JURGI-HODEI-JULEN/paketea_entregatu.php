<?php
include 'konexioa.php';

// Inprimakia bidali bada
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Egiaztatu entregatzeko botoiren batean klik egin den
    foreach ($_POST as $key => $value) {
        if (strpos($key, 'entregado_') !== false) {
            // Lortu eskaeraren IDa botoiaren izenetik
            $pedido_id = substr($key, strlen('entregado_'));
            $nota = $_POST['nota_' . $pedido_id]; // Lortu dagokion oharrak
            
            // "entregatuta" zutabearen balioa eguneratu datu-basean dagokion erregistroarentzat
            $sql_update = "UPDATE paketea SET entregatuta = 1 WHERE ID = ?";
            $stmt_update = $conn->prepare($sql_update);
            if ($stmt_update === false) {
                die('Prestaketa huts egin du: ' . htmlspecialchars($conn->error));
            }
            $stmt_update->bind_param("i", $pedido_id); // 'i'-k parametroa zenbakia dela adierazten du
            if (!$stmt_update->execute()) {
                die('Exekuzioa huts egin du: ' . htmlspecialchars($stmt_update->error));
            }
            $stmt_update->close();
            
            // Oharra eguneratu historiala taulan eguneratzearen ondoren
            $sql_update_historiala = "UPDATE historiala SET oharra = ? WHERE ID = ?";
            $stmt_update_historiala = $conn->prepare($sql_update_historiala);
            if ($stmt_update_historiala === false) {
                die('Prestaketa huts egin du: ' . htmlspecialchars($conn->error));
            }
            $stmt_update_historiala->bind_param("si", $nota, $pedido_id); // 's'-k lehen parametroa kate bat dela adierazten du, 'i'-k bigarrena zenbakia dela adierazten du
            if (!$stmt_update_historiala->execute()) {
                die('Exekuzioa huts egin du: ' . htmlspecialchars($stmt_update_historiala->error));
            }
            $stmt_update_historiala->close();

            // Paketearen entregatuta bezala markatuta dauden errenkadak ezabatu
            $sql_delete = "DELETE FROM paketea WHERE entregatuta = 1";
            if (!$conn->query($sql_delete)) {
                die('Ezabaketan errorea: ' . htmlspecialchars($conn->error));
            }
        }
    }
}

$conn->close();

// Eragiketa egin ondoren atzera orrialde nagusira birbideratu
header("Location: kudeatu.php");
exit();
?>
