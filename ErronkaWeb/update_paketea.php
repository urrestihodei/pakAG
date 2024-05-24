<?php
include 'konexioa.php';

if (!isset($_SESSION['username'])) {
    die('Usuario no autenticado');
}

$response = array('status' => 'error', 'message' => 'Errorea.');

if (isset($_POST['pakete_id'])) {
    $pakete_id = $_POST['pakete_id'];

    // Comprobar si ya hay un paquete con zein_entregatu = 1
    $check_sql = "SELECT ID FROM paketea WHERE zein_entregatu = 1";
    $check_result = $conn->query($check_sql);

    if ($check_result->num_rows > 0) {
        $existing_pakete = $check_result->fetch_assoc();
        $existing_pakete_id = $existing_pakete['ID'];

        // Si ya hay un paquete con zein_entregatu = 1, preguntar confirmación
        if (isset($_POST['confirm']) && $_POST['confirm'] == 'true') {
            // Actualizar el paquete existente a zein_entregatu = 0
            $reset_sql = "UPDATE paketea SET zein_entregatu = 0 WHERE ID = ?";
            $reset_stmt = $conn->prepare($reset_sql);
            if ($reset_stmt === false) {
                die('Prepare failed: ' . htmlspecialchars($conn->error));
            }
            $reset_stmt->bind_param("i", $existing_pakete_id);
            if (!$reset_stmt->execute()) {
                die('Execute failed: ' . htmlspecialchars($reset_stmt->error));
            }
            $reset_stmt->close();

            // Actualizar el nuevo paquete a zein_entregatu = 1
            $update_sql = "UPDATE paketea SET zein_entregatu = 1 WHERE ID = ?";
            $update_stmt = $conn->prepare($update_sql);
            if ($update_stmt === false) {
                die('Prepare failed: ' . htmlspecialchars($conn->error));
            }
            $update_stmt->bind_param("i", $pakete_id);
            if (!$update_stmt->execute()) {
                die('Execute failed: ' . htmlspecialchars($update_stmt->error));
            } else {
                $response = array('status' => 'success', 'message' => "$pakete_id ID-a duen paketea ondo aktualizatu da.");
            }
            $update_stmt->close();
        } else {
            $response = array('status' => 'confirm', 'message' => "Dagoeneko aukeratua duzu entregatzeko pakete bat, $existing_pakete_id ID-a duena. Aktualizatu nahi duzu?");
        }
    } else {
        // Si no hay paquete con zein_entregatu = 1, actualizar el paquete directamente
        $update_sql = "UPDATE paketea SET zein_entregatu = 1 WHERE ID = ?";
        $update_stmt = $conn->prepare($update_sql);
        if ($update_stmt === false) {
            die('Prepare failed: ' . htmlspecialchars($conn->error));
        }
        $update_stmt->bind_param("i", $pakete_id);
        if (!$update_stmt->execute()) {
            die('Execute failed: ' . htmlspecialchars($update_stmt->error));
        } else {
            $response = array('status' => 'success', 'message' => "$pakete_id  ID-a duen paketea ondo aktualizatu da.");
        }
        $update_stmt->close();
    }
}

$conn->close();

echo json_encode($response);
?>
