<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true" %>
<%
    String rol = (String) session.getAttribute("rol");
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
    <script src="<%= request.getContextPath() %>/js/intranet.js" defer></script>
</head>
<body>

<% if ("ok".equals(request.getParameter("login"))) { %>
    <p style="color: green;">✅ Has iniciado sesión con éxito.</p>
<% } %>

<div class="container">
    <div class="welcome-message">
        <h2>Bienvenido a tu intranet, <%= session.getAttribute("usuarioNombre") %>.</h2>
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
    </div>
</div>

<!-- Enlaces a las zonas -->
<div id="zones-content">
    <div id="personal" class="zone-content" style="display: block;">
        <form action="<%= request.getContextPath() %>/jsp/ZonaPersonal.jsp" method="get">
            <button type="submit">🧍 Entrar en Zona Personal</button>
        </form>
    </div>

    <div id="corporativa" class="zone-content" style="display: none;">
        <form action="<%= request.getContextPath() %>/jsp/ZonaCorporativa.jsp" method="get">
            <button type="submit">🏢 Entrar en Zona Corporativa</button>
        </form>
    </div>
</div>

<!-- Logout -->
<form action="<%= request.getContextPath() %>/control" method="post">
    <input type="hidden" name="action" value="logout">
    <button type="submit">Cerrar sesión</button>
</form>

</body>
</html>
