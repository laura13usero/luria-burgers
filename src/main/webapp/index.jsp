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
    <title>Home Page</title>
</head>
<body>
    <h1>Welcome to the Home Page </h1>

    <% if (usuario != null) { %>
        <p>Hello, <strong><%= usuario.getNombre() %></strong>. You are logged in.</p>
    <% } else { %>
        <p>Welcome, Please log in to access more features.</p>
    <% } %>

    <% if (usuario == null) { %>
        <!-- Botón de registrarse -->
        <form action="<%= request.getContextPath() %>/jsp/registro.jsp" method="get">
            <button type="submit">Sign Up!</button>
        </form>

        <!-- Botón de iniciar sesión -->
        <form action="<%= request.getContextPath() %>/jsp/login.jsp" method="get">
            <button type="submit">Log In</button>
        </form>
    <% } %>

    <!-- Botón de login para la Intranet -->
    <form action="<%= request.getContextPath() %>/jsp/loginintranet.jsp" method="get">
        <button type="submit" style="background-color: #333; color: #fff;">Login Intranet</button>
    </form>

    <!-- Botón ver hamburguesas -->
    <form action="<%= request.getContextPath() %>/control" method="get">
        <input type="hidden" name="action" value="hamburguesas">
        <button type="submit">Burgers</button>
    </form>

    <!-- Botón ver bebidas -->
    <form action="<%= request.getContextPath() %>/control" method="get">
        <input type="hidden" name="action" value="bebidas">
        <button type="submit">Drinks</button>
    </form>

    <!-- Botón ver postres -->
    <form action="<%= request.getContextPath() %>/control" method="get" style="margin-bottom: 10px;">
        <input type="hidden" name="action" value="postres">
        <button type="submit" style="background-color: #f8b400; color: #fff; padding: 8px 16px; border: none; border-radius: 5px;">
            Desserts 🍰
        </button>
    </form>

    <!-- Botón ver guarniciones -->
    <form action="<%= request.getContextPath() %>/control" method="get" style="margin-bottom: 10px;">
        <input type="hidden" name="action" value="guarniciones">
        <button type="submit">Side Dishes</button>
    </form>

    <!-- Botón de ver carrito (unificado) -->
    <form action="<%= request.getContextPath() %>/control" method="get" style="margin-top: 10px;">
        <input type="hidden" name="action" value="verCarrito">
        <button type="submit">Cart</button>
    </form>

    <% if (usuario != null) { %>
        <!-- Botón de cerrar sesión -->
        <form action="<%= request.getContextPath() %>/control" method="post" style="margin-top: 20px;">
            <input type="hidden" name="action" value="logout">
            <button type="submit">Log out</button>
        </form>
    <% } %>

</body>
</html>
