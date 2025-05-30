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
</head>
<body>

<% if ("ok".equals(request.getParameter("login"))) { %>
    <p style="color: green;">✅ Has iniciado sesión con éxito.</p>
<% } %>

<h1>Bienvenido, ${usuario.nombre}!</h1>
<h2>Panel de Empleado</h2>

<p>Accede a las funcionalidades disponibles para tu rol:</p>
<ul>
    <li><a href="${pageContext.request.contextPath}/verPedidos">Ver Pedidos</a></li>
    <li><a href="${pageContext.request.contextPath}/gestionarProductos">Gestionar Productos</a></li>
</ul>

<form action="<%= request.getContextPath() %>/control" method="post" style="margin-top: 20px;">
            <input type="hidden" name="action" value="logout">
            <button type="submit">Cerrar sesión</button>
</form>

</body>
</html>
