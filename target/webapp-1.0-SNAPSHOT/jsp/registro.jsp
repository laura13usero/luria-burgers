<!-- src/main/webapp/registro.jsp -->
<!DOCTYPE html>
<html>
<head>
    <title>Registro</title>
</head>
<body>
    <h2>Registro de Usuario</h2>
    <form action="/webapp-1.0-SNAPSHOT/control?action=registro" method="post">
        Nombre: <input type="text" name="nombre" required><br>
        Email: <input type="email" name="email" required><br>
        Contraseña: <input type="password" name="contrasena" required><br>
        Teléfono: <input type="text" name="telefono" required><br>
        Dirección: <input type="text" name="direccion" required><br>
        <input type="submit" value="Registrarse">
    </form>

</body>
</html>
