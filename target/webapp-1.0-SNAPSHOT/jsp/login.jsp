<!-- src/main/webapp/login.jsp -->
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>
    <form action="/webapp-1.0-SNAPSHOT/control?action=login" method="post">
        Email: <input type="email" name="email" required><br>
        Contraseña: <input type="password" name="contrasena" required><br>
        <input type="submit" value="Iniciar sesión">
    </form>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
</body>
</html>
