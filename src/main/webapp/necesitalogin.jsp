<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>You need to log in</title>
</head>
<body>

    <h1>To view the cart, you need to register or log in!</h1>

    <form action="<%= request.getContextPath() %>/jsp/login.jsp" method="get">
        <button type="submit">Please, Log In</button>
    </form>

    <form action="<%= request.getContextPath() %>/jsp/registro.jsp" method="get">
            <button type="submit">Or Sign up!</button>
        </form>

    <form action="<%= request.getContextPath() %>/jsp/index.jsp" method="get">
        <button type="submit"> Go back to the homepage</button>
    </form>

</body>
</html>
