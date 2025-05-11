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

<form action="<%= request.getContextPath() %>/jsp/emplecalendario.jsp" method="get">
    <button type="submit">Calendario</button>
</form>
<form action="<%= request.getContextPath() %>/jsp/emplecertificados.jsp" method="get">
    <button type="submit">Certificados</button>
</form>
<form action="<%= request.getContextPath() %>/jsp/empleciberseguridad.jsp" method="get">
    <button type="submit">Ciberseguridad</button>
</form>
<form action="<%= request.getContextPath() %>/jsp/emplecomite.jsp" method="get">
    <button type="submit">Comité</button>
</form>
<form action="<%= request.getContextPath() %>/jsp/emplecultura.jsp" method="get">
    <button type="submit">Cultura</button>
</form>
<form action="<%= request.getContextPath() %>/jsp/emplledenuncias.jsp" method="get">
    <button type="submit">Denuncias</button>
</form>
<form action="<%= request.getContextPath() %>/jsp/emplenomina.jsp" method="get">
    <button type="submit">Nómina</button>
</form>
<form action="<%= request.getContextPath() %>/jsp/emplenormativa.jsp" method="get">
    <button type="submit">Normativa</button>
</form>
<form action="<%= request.getContextPath() %>/jsp/emplenoticias.jsp" method="get">
    <button type="submit">Noticias</button>
</form>
<form action="<%= request.getContextPath() %>/jsp/emplsalud.jsp" method="get">
    <button type="submit">Salud</button>
</form>
<form action="<%= request.getContextPath() %>/jsp/emplsostenibilidad.jsp" method="get">
    <button type="submit">Sostenibilidad</button>
</form>
<form action="<%= request.getContextPath() %>/jsp/emplevacaciones.jsp" method="get">
    <button type="submit">Vacaciones</button>
</form>

<form action="<%= request.getContextPath() %>/control" method="post">
    <input type="hidden" name="action" value="logout">
    <button type="submit">Cerrar sesión</button>
</form>

</body>
</html>
