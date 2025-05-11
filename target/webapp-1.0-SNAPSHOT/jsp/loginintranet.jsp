<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login Intranet</title>
</head>
<body>
    <h1>Iniciar sesión en la Intranet</h1>
    <form action="${pageContext.request.contextPath}/control?action=login" method="post">
        <input type="hidden" name="action" value="UserLoginAction">
        <input type="hidden" name="origen" value="intranet"> <!-- para distinguirlo -->

        Email: <input type="text" name="email" required><br>
        Contraseña: <input type="password" name="contrasena" required><br>

        <input type="submit" value="Entrar">
    </form>
</body>
</html>
