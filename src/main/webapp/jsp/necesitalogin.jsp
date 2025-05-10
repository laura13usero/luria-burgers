<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Necesitas Iniciar Sesión</title>
</head>
<body>

    <h1>Para ver el carrito necesitas registrarte o iniciar sesión.</h1>

    <form action="<%= request.getContextPath() %>/jsp/login.jsp" method="get">
        <button type="submit">Iniciar sesión</button>
    </form>

    <form action="<%= request.getContextPath() %>/jsp/registro.jsp" method="get">
            <button type="submit">Regístrate</button>
        </form>

    <form action="<%= request.getContextPath() %>/jsp/index.jsp" method="get">
        <button type="submit">Volver a la página principal</button>
    </form>

</body>
</html>
