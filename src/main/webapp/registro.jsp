<!-- src/main/webapp/registro.jsp -->
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up!</title>
</head>
<body>
    <h2>User Registration</h2>
    <form action="/webapp-1.0-SNAPSHOT/control?action=registro" method="post">
        Name: <input type="text" name="nombre" required><br>
        E-mail: <input type="email" name="email" required><br>
        Password: <input type="password" name="contrasena" required><br>
        Phone Number: <input type="text" name="telefono" required><br>
        Address: <input type="text" name="direccion" required><br>
        <input type="submit" value="Sign Up!">
    </form>

</body>
</html>
