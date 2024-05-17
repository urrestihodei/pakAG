<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kudeatu</title>
    <!-- Incluir Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Incluir CSS personalizado -->
    <link rel="stylesheet" href="styles.css">
    <style>
        body {
            background: #2d2a29; /* Aplica un gradiente vertical */
        }
        .hidden {
            display: none;
        }
        .side-buttons {
            flex: 0 0 200px;
        }
        .content {
            flex: 1;
        }
        .side-buttons .btn {
            margin-bottom: 10px; /* Separación entre los botones */
            background-color: #ff0037; /* Color de fondo del botón */
            border: none; /* Quita el borde */
            border-radius: 8px; /* Borde redondeado */
        }
        .side-buttons .btn:hover {
            background-color: #b3005a; /* Cambia el color de fondo al pasar el ratón */
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="card">
            <div class="card-header">
                <h1>Kudeaketa panela</h1>
            </div>
            <div class="card-body d-flex">
                <div class="d-flex flex-column side-buttons mr-5">
                    <button id="paketeaButton" class="btn btn-primary">PAKETEAK</button>
                    <button id="historialaButton" class="btn btn-primary">HISTORIALA</button>
                    <button id="logoutButton" class="btn btn-primary">LOGOUT</button>
                </div>
                <div class="content">
                    <!-- Contenedor para la información de PAKETEAK -->
                    <div id="paketeaContent" class="mt-3 hidden"></div>
                    <!-- Contenedor para la información de HISTORIALA -->
                    <div id="historialaContent" class="mt-3 hidden"></div>
                </div>
            </div>
        </div>
    </div>
    <!-- Incluir Bootstrap JS y dependencias de Popper.js -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <!-- Incluir jQuery -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <!-- Script para manejar la carga de contenido con AJAX -->
    <script>
        document.getElementById('paketeaButton').addEventListener('click', function() {
            var paketeaContent = document.getElementById('paketeaContent');
            var historialaContent = document.getElementById('historialaContent');
            if (paketeaContent.classList.contains('hidden')) {
                fetch('load_paketea.php')
                    .then(response => response.text())
                    .then(data => {
                        paketeaContent.innerHTML = data;
                        paketeaContent.classList.remove('hidden');
                        historialaContent.classList.add('hidden'); // Hide historiala content if visible
                    })
                    .catch(error => console.error('Error al cargar los datos:', error));
            } else {
                paketeaContent.classList.add('hidden');
            }
        });

        document.getElementById('historialaButton').addEventListener('click', function() {
            var historialaContent = document.getElementById('historialaContent');
            var paketeaContent = document.getElementById('paketeaContent');
            if (historialaContent.classList.contains('hidden')) {
                fetch('load_historiala.php')
                    .then(response => response.text())
                    .then(data => {
                        historialaContent.innerHTML = data;
                        historialaContent.classList.remove('hidden');
                        paketeaContent.classList.add('hidden'); // Hide paketea content if visible
                    })
                    .catch(error => console.error('Error al cargar los datos:', error));
            } else {
                historialaContent.classList.add('hidden');
            }
        });

        document.getElementById('logoutButton').addEventListener('click', function() {
            fetch('logout.php')
                .then(response => {
                    if (response.ok) {
                        window.location.href = 'index.html'; // Redirigir a la página de inicio de sesión
                    } else {
                        console.error('Error al intentar cerrar sesión.');
                    }
                })
                .catch(error => console.error('Error al intentar cerrar sesión:', error));
        });

        // Delegar el evento de clic en el botón "Entregado" a todo el documento
        document.addEventListener('click', function(e) {
            if (e.target && e.target.classList.contains('entregadoButton')) {
                var pedidoId = e.target.getAttribute('data-id');
                fetch('update_paketea.php', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: 'entregado_' + pedidoId + '=1'
                })
                .then(response => response.text())
                .then(data => {
                    // Recargar el contenido de PAKETEAK después de la actualización
                    document.getElementById('paketeaButton').click();
                    document.getElementById('paketeaButton').click(); // Toggle visibility back on
                })
                .catch(error => console.error('Error al actualizar los datos:', error));
            }
        });
    </script>
</body>
</html>
