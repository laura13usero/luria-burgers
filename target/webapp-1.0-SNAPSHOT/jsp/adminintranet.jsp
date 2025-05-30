<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true" %>
<%
    String rol = (String) session.getAttribute("rol");
    if (!"admin".equals(rol)) {
        response.sendRedirect(request.getContextPath() + "/jsp/accesoDenegado.jsp");
        return;
    }
%>
<html>
<head>
    <title>Intranet - Administrador</title>
</head>
<body>

<% if ("ok".equals(request.getParameter("login"))) { %>
    <p style="color: green;">✅ Has iniciado sesión con éxito.</p>
<% } %>

<h1>Bienvenido, ${usuario.nombre}!</h1>
<h2>Panel de Administrador</h2>

<p>Accede a las funcionalidades administrativas:</p>
<ul>
    <li><a href="${pageContext.request.contextPath}/jsp/registrarEmpleado.jsp">Gestionar Plantilla</a></li>
    <li><a href="${pageContext.request.contextPath}/gestionarPedidos">Gestionar Pedidos</a></li>
    <li><a href="${pageContext.request.contextPath}/gestionarRoles">Gestionar Roles</a></li>
</ul>

        <!-- Botón de cerrar sesión -->
        <form action="<%= request.getContextPath() %>/control" method="post" style="margin-top: 20px;">
            <input type="hidden" name="action" value="logout">
            <button type="submit">Cerrar sesión</button>
        </form>

</body>
</html>
