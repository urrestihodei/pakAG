<?php

    session_start();

    $zerbitzaria 	= 'localhost'; // SQL Zerbitzaria
    $erabiltzailea 	= 'root'; 			// Konexioa egingo duen erabiltzailea
    $password 		= ''; 				// Erabiltzailearen pasahitza
    $db 			= 'pakag';		// Konexioa erabiliko duen datubasea
    $conn 			= new mysqli($zerbitzaria, $erabiltzailea, $password, $db);

    if ($conn->connect_error) { // Konexioan erroreak ez daudela konprobatu
        $_SESSION["error"] = "DBarekin konektatzerakoan akatsa: " . $conn->connect_error;
        die;
    }

?>