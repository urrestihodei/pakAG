<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>pakAG banaketak</title>
    <!-- Bootstrap CSS -->
    <link rel="icon" href="images/favicon2.png" type="image/x-icon">
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- CSS personalizatua -->
    <link rel="stylesheet" href="styles.css">
    <style>
        body {
            background: #2d2a29;
        }
        .hidden {
            display: none;
        }
        .side-buttons {
            flex: 0 0 200px;
        }
        .content, .new-content {
            overflow-y: auto;
            max-height: 600px;
        }
        .side-buttons .btn {
            margin-bottom: 10px;
            background-color: #ff0037;
            border: none;
            border-radius: 8px;
        }
        .side-buttons .btn:hover {
            background-color: #b3005a;
        }
        .row-no-gutters {
            margin-left: -15px;
            margin-right: -15px;
        }
        .expanded-content {
            max-height: none;
        }
    </style>
</head>
<body>
    <div class="container-fluid mt-5">
        <div class="row row-no-gutters">
            <div class="col-lg-9 mb-4">
                <div id="kudeaketaPanel" class="card">
                    <div class="card-header">
                        <h1>Banaketa panela</h1>
                    </div>
                    <div class="card-body d-flex">
                        <div class="d-flex flex-column side-buttons mr-5">
                            <button id="paketeaButton" class="btn btn-primary">PAKETEAK</button>
                            <button id="historialaButton" class="btn btn-primary">HISTORIALA</button>
                            <button id="logoutButton" class="btn btn-primary">LOGOUT</button>
                        </div>
                        <div class="content w-100">
                            <div id="paketeaContent" class="mt-3 hidden"></div>
                            <div id="historialaContent" class="mt-3 hidden"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-3 mb-4">
                <div class="card">
                    <div class="card-header">
                        <h2>Uneko banaketa</h2>
                    </div>
                    <div class="card-body new-content">
                        <div id="unekoContent" class="mt-3"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function() {
            function loadUnekoPaketea() {
                $.ajax({
                    url: 'kargatu_uneko_paketea.php',
                    method: 'GET',
                    success: function(response) {
                        $('#unekoContent').html(response);
                        eval($("#unekoContent script").text());
                    },
                    error: function(xhr, status, error) {
                        console.error('Error en la solicitud AJAX: ' + error);
                    }
                });
            }

            $('#paketeaButton').click(function() {
                $.ajax({
                    url: 'load_paketea.php',
                    method: 'GET',
                    success: function(response) {
                        $('#paketeaContent').html(response).removeClass('hidden');
                        $('#historialaContent').addClass('hidden');
                    },
                    error: function(xhr, status, error) {
                        console.error('Error en la solicitud AJAX: ' + error);
                    }
                });
            });

            $('#historialaButton').click(function() {
                $.ajax({
                    url: 'load_historiala.php',
                    method: 'GET',
                    success: function(response) {
                        $('#historialaContent').html(response).removeClass('hidden');
                        $('#paketeaContent').addClass('hidden');
                    },
                    error: function(xhr, status, error) {
                        console.error('Error en la solicitud AJAX: ' + error);
                    }
                });
            });

            $('#logoutButton').click(function() {
    $.ajax({
        url: 'logout.php',
        method: 'POST',
        success: function(response) {
            var result = JSON.parse(response);
            if (result.status === 'success') {
                window.location.href = 'index.html';
            } else {
                alert(result.message);
            }
        },
        error: function(xhr, status, error) {
            console.error('Error en la solicitud AJAX: ' + error);
        }
    });
});

            $(document).on('click', '.entregar-btn', function() {
                var paketeId = $(this).data('id');
                $.ajax({
                    url: 'update_paketea.php',
                    method: 'POST',
                    data: { pakete_id: paketeId },
                    success: function(response) {
                        var result = JSON.parse(response);
                        if (result.status === 'confirm') {
                            if (confirm(result.message)) {
                                confirmUpdate(paketeId);
                            }
                        } else {
                            alert(result.message);
                            location.reload(); // Kargatu orria aldaketak ikusteko
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('Error en la solicitud AJAX: ' + error);
                    }
                });
            });

            function confirmUpdate(paketeId) {
                $.ajax({
                    url: 'update_paketea.php',
                    method: 'POST',
                    data: { pakete_id: paketeId, confirm: 'true' },
                    success: function(response) {
                        var result = JSON.parse(response);
                        alert(result.message);
                        location.reload();
                    },
                    error: function(xhr, status, error) {
                        console.error('Error en la solicitud AJAX: ' + error);
                    }
                });
            }

            loadUnekoPaketea();
        });
    </script>
</body>
</html>
