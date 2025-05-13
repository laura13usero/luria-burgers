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
    <!-- Estilos del Footer -->
    <style>
        /* Ajuste del footer */
        .footer {
            margin-top: 2rem;
            padding: 1rem 0;
            text-align: center;
            background-color: transparent;
            color: white;
        }
        .footer .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 15px;
        }
        .footer a {
            color: white;
            text-decoration: none;
        }
        .footer a:hover {
            text-decoration: underline;
        }
    </style>
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

<!-- Footer -->
<footer class="footer">
    <div class="container">
        <p>© 2025 Luría's Burger – Intranet</p>
        <p><a href="#">Política de privacidad</a> | <a href="#">Soporte técnico</a></p>
    </div>
</footer>

</body>
</html>
