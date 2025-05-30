<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login Exitoso</title>
</head>
<body>
    <h1>¡Login exitoso!</h1>
    <p id="mensajeBienvenida">Cargando datos del usuario...</p>

    <button onclick="window.location.href='index.html'">Volver al inicio</button>

    <button onclick="logout()">Cerrar sesión</button>

    <script>
        async function obtenerUsuario() {
            try {
                const res = await fetch('/webapp-1.0-SNAPSHOT/control?action=getUsuarioLogueado');
                const data = await res.json();
                if (data.status === 'ok') {
                    document.getElementById('mensajeBienvenida').textContent =
                        `¡Bienvenido! ようこそ, ${data.nombre}`;
                } else {
                    document.getElementById('mensajeBienvenida').textContent =
                        'Error al obtener los datos del usuario. Inicia sesión de nuevo.';
                }
            } catch (error) {
                document.getElementById('mensajeBienvenida').textContent =
                    'Error de conexión con el servidor.';
            }
        }

        async function logout() {
            try {
                const res = await fetch('/webapp-1.0-SNAPSHOT/control?action=logout', {
                    method: 'POST'
                });
                const data = await res.json();
                if (data.status === 'ok') {
                    window.location.href = 'index.html';
                } else {
                    alert('Error al cerrar sesión');
                }
            } catch (e) {
                alert('Error de conexión');
            }
        }

        obtenerUsuario();
    </script>
</body>
</html>
