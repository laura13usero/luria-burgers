<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Acceso Denegado</title>
</head>
<body>
    <h1>🚫 Acceso Denegado</h1>
    <p>No tienes permisos para acceder a esta página.</p>
    <form action="<%= request.getContextPath() %>/jsp/index.jsp" method="get">
        <button type="submit">Volver al inicio</button>
    </form>
</body>
</html>
