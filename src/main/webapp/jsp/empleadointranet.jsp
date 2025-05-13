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
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <meta name="description" content="Intranet de Luría's Burger">
    <meta name="theme-color" content="#D05A31">
    <title>Intranet - Empleado</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/intranet.css">
    <link rel="icon" href="<%= request.getContextPath() %>/assets/fondos_recursos/flor.png" type="image/png">
</head>
<body>

<div class="container">
    <% if ("ok".equals(request.getParameter("login"))) { %>
        <p style="color: green;">✅ Has iniciado sesión con éxito.</p>
    <% } %>

    <div class="welcome-message">
        <h1>Bienvenido, ${usuario.nombre}!</h1>
        <p>Accede a todas las herramientas y recursos que necesitas como miembro del equipo Luría's Burger.</p>
    </div>

    <div class="tabs-container">
      <div class="zone-tabs" role="tablist">
        <a href="<%= request.getContextPath() %>/jsp/ZonaPersonal.jsp"
           class="zone-tab active" role="tab" aria-selected="true" tabindex="0">
          Zona Personal
        </a>
        <a href="<%= request.getContextPath() %>/jsp/ZonaCorporativa.jsp"
           class="zone-tab" role="tab" aria-selected="false" tabindex="0">
          Zona Corporativa
        </a>
      </div>
    </div>


    <form action="<%= request.getContextPath() %>/control" method="post">
        <input type="hidden" name="action" value="logout">
        <button type="submit" class="logout-button">Cerrar sesión</button>
    </form>
</div>

<footer class="footer">
    <div class="container">
        <p>© 2025 Luría's Burger – Intranet</p>
        <p><a href="#">Política de privacidad</a> | <a href="#">Soporte técnico</a></p>
    </div>
</footer>

</body>
</html>
