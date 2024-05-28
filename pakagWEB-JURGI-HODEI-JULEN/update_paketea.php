<?php
include 'konexioa.php';

if (!isset($_SESSION['username'])) {
    die('Erabiltzailea ez da autentikatu');
}

$response = array('status' => 'error', 'message' => 'Errorea.');

if (isset($_POST['pakete_id'])) {
    $pakete_id = $_POST['pakete_id'];

    // Zein_entregatu = 1 duten paketerik dagoen egiaztatu
    $check_sql = "SELECT ID FROM paketea WHERE zein_entregatu = 1";
    $check_result = $conn->query($check_sql);

    if ($check_result->num_rows > 0) {
        $existing_pakete = $check_result->fetch_assoc();
        $existing_pakete_id = $existing_pakete['ID'];

        // Zein_entregatu = 1 duten pakete bat badago, baieztapena eskatu
        if (isset($_POST['confirm']) && $_POST['confirm'] == 'true') {
            // Dauden paketea eguneratu zein_entregatu = 0-ra
            $reset_sql = "UPDATE paketea SET zein_entregatu = 0 WHERE ID = ?";
            $reset_stmt = $conn->prepare($reset_sql);
            if ($reset_stmt === false) {
                die('Prestaketa huts egin du: ' . htmlspecialchars($conn->error));
            }
            $reset_stmt->bind_param("i", $existing_pakete_id);
            if (!$reset_stmt->execute()) {
                die('Exekuzioa huts egin du: ' . htmlspecialchars($reset_stmt->error));
            }
            $reset_stmt->close();

            // Pakete berria eguneratu zein_entregatu = 1-ra
            $update_sql = "UPDATE paketea SET zein_entregatu = 1 WHERE ID = ?";
            $update_stmt = $conn->prepare($update_sql);
            if ($update_stmt === false) {
                die('Prestaketa huts egin du: ' . htmlspecialchars($conn->error));
            }
            $update_stmt->bind_param("i", $pakete_id);
            if (!$update_stmt->execute()) {
                die('Exekuzioa huts egin du: ' . htmlspecialchars($update_stmt->error));
            } else {
                $response = array('status' => 'success', 'message' => "$pakete_id ID-a duen paketea ondo aktualizatu da.");
            }
            $update_stmt->close();
        } else {
            $response = array('status' => 'confirm', 'message' => "Dagoeneko aukeratua duzu entregatzeko pakete bat, $existing_pakete_id ID-a duena. Aktualizatu nahi duzu?");
        }
    } else {
        // Zein_entregatu = 1 duen paketerik ez badago, paketea zuzenean eguneratu
        $update_sql = "UPDATE paketea SET zein_entregatu = 1 WHERE ID = ?";
        $update_stmt = $conn->prepare($update_sql);
        if ($update_stmt === false) {
            die('Prestaketa huts egin du: ' . htmlspecialchars($conn->error));
        }
        $update_stmt->bind_param("i", $pakete_id);
        if (!$update_stmt->execute()) {
            die('Exekuzioa huts egin du: ' . htmlspecialchars($update_stmt->error));
        } else {
            $response = array('status' => 'success', 'message' => "$pakete_id  ID-a duen paketea ondo aktualizatu da.");
        }
        $update_stmt->close();
    }
}

$conn->close();

echo json_encode($response);
?>
