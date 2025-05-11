<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Usuario" %>

<%
    Usuario usuario = null;
    if (session != null) {
        usuario = (Usuario) session.getAttribute("usuarioLogueado");
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página Principal</title>
</head>
<body style="background-color:red;">
    <h1>Bienvenido a la Página Principal</h1>

    <% if (usuario != null) { %>
        <p>Hola, <strong><%= usuario.getNombre() %></strong>. Estás logueado.</p>
    <% } else { %>
        <p>Bienvenido, por favor inicia sesión para acceder a más funcionalidades.</p>
    <% } %>

    <% if (usuario == null) { %>
        <!-- Botón de registrarse -->
        <form action="<%= request.getContextPath() %>/jsp/registro.jsp" method="get">
            <button type="submit">Regístrate</button>
        </form>

        <!-- Botón de iniciar sesión -->
        <form action="<%= request.getContextPath() %>/jsp/login.jsp" method="get">
            <button type="submit">Iniciar sesión</button>
        </form>
    <% } %>

    <!-- Botón de login para la Intranet -->
    <form action="<%= request.getContextPath() %>/jsp/loginintranet.jsp" method="get">
        <button type="submit" style="background-color: #333; color: #fff;">Login Intranet</button>
    </form>

    <!-- Botón ver hamburguesas -->
    <form action="<%= request.getContextPath() %>/control" method="get">
        <input type="hidden" name="action" value="hamburguesas">
        <button type="submit">Ver Hamburguesas</button>
    </form>

    <!-- Botón ver bebidas -->
    <form action="<%= request.getContextPath() %>/control" method="get">
        <input type="hidden" name="action" value="bebidas">
        <button type="submit">Ver Bebidas</button>
    </form>

    <!-- Botón ver postres -->
    <form action="<%= request.getContextPath() %>/control" method="get" style="margin-bottom: 10px;">
        <input type="hidden" name="action" value="postres">
        <button type="submit" style="background-color: #f8b400; color: #fff; padding: 8px 16px; border: none; border-radius: 5px;">
            Ver Postres 🍰
        </button>
    </form>

    <!-- Botón ver guarniciones -->
    <form action="<%= request.getContextPath() %>/control" method="get" style="margin-bottom: 10px;">
        <input type="hidden" name="action" value="guarniciones">
        <button type="submit">Ver Guarniciones</button>
    </form>

    <!-- Botón de ver carrito (unificado) -->
    <form action="<%= request.getContextPath() %>/control" method="get" style="margin-top: 10px;">
        <input type="hidden" name="action" value="verCarrito">
        <button type="submit">Ver mi carrito</button>
    </form>

    <% if (usuario != null) { %>
        <!-- Botón de cerrar sesión -->
        <form action="<%= request.getContextPath() %>/control" method="post" style="margin-top: 20px;">
            <input type="hidden" name="action" value="logout">
            <button type="submit">Cerrar sesión</button>
        </form>
    <% } %>

</body>
</html>
