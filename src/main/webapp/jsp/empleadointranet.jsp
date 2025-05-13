<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true" %>
<%@ page import="modelo.Usuario" %>

<%
    String rol = (String) session.getAttribute("rol");
    Usuario usuario = (Usuario) session.getAttribute("usuario");

    if (!"empleado".equals(rol)) {
        response.sendRedirect(request.getContextPath() + "/jsp/accesoDenegado.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Intranet - Empleado</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/intranet.css">
    <script src="<%= request.getContextPath() %>/js/tabs.js" defer></script> <!-- si tienes JS -->
</head>
<body>

<%-- Mensaje de login correcto --%>
<% if ("ok".equals(request.getParameter("login"))) { %>
    <p style="color: green;">✅ Has iniciado sesión con éxito.</p>
<% } %>

<div class="container">
    <div class="welcome-message">
        <h2>Bienvenido a tu intranet, <%= usuario.getNombre() %>.</h2>
        <p>Accede a todas las herramientas y recursos que necesitas como miembro del equipo Luría's Burger.</p>
    </div>

    <div class="tabs-container">
        <div class="zone-tabs" role="tablist">
            <div class="zone-tab active" role="tab" aria-selected="true" tabindex="0"
                 onclick="openZone(event,'personal')" onkeydown="handleZoneKeyDown(event,'personal')">
                Zona Personal
            </div>
            <div class="zone-tab" role="tab" aria-selected="false" tabindex="0"
                 onclick="openZone(event,'corporativa')" onkeydown="handleZoneKeyDown(event,'corporativa')">
                Zona Corporativa
            </div>
        </div>

        <div class="zone-content">
            <!-- Aquí podrías usar <jsp:include> para incluir contenido de ZonaPersonal.jsp o ZonaCorporativa.jsp -->
            <form action="<%= request.getContextPath() %>/jsp/ZonaPersonal.jsp" method="get">
                <button type="submit">🧍 Zona Personal</button>
            </form>
            <form action="<%= request.getContextPath() %>/jsp/ZonaCorporativa.jsp" method="get">
                <button type="submit">🏢 Zona Corporativa</button>
            </form>
        </div>
    </div>

    <form action="<%= request.getContextPath() %>/control" method="post">
        <input type="hidden" name="action" value="logout">
        <button type="submit">Cerrar sesión</button>
    </form>
</div>

</body>
</html>
