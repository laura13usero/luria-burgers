<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true" %>
<%
    String rol = (String) session.getAttribute("rol");
    if (!"empleado".equals(rol)) {
        response.sendRedirect(request.getContextPath() + "/jsp/accesoDenegado.jsp");
        return;
    }
%>
<html>
<head>
    <title>Intranet - Empleado</title>

        <meta charset="UTF-8">
        <title>Intranet - Empleado</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/intranet.css">
</head>
<body>

<% if ("ok".equals(request.getParameter("login"))) { %>
    <p style="color: green;">✅ Has iniciado sesión con éxito.</p>
<% } %>

<h1>Bienvenido, ${usuario.nombre}!</h1>
<h2>Zona de Empleado</h2>

<form action="<%= request.getContextPath() %>/jsp/ZonaPersonal.jsp" method="get">
    <button type="submit">🧍 Zona Personal</button>
</form>
<form action="<%= request.getContextPath() %>/jsp/ZonaCorporativa.jsp" method="get">
    <button type="submit">🏢 Zona Corporativa</button>
</form>

<form action="<%= request.getContextPath() %>/control" method="post">
    <input type="hidden" name="action" value="logout">
    <button type="submit">Cerrar sesión</button>
</form>

</body>
</html>
