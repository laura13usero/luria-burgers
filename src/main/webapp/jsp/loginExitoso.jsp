<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Usuario" %>

<%
    // Obtener la sesión actual (usando el objeto implícito session)
    Usuario usuario = null;

    // Si hay sesión, obtener el usuario logueado
    if (session != null) {
        usuario = (Usuario) session.getAttribute("usuarioLogueado");
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Successful Login</title>
</head>
<body>
    <h1>Successful Login!</h1>

    <% if (usuario != null) { %>
        <p>Welcome! ようこそ! <strong><%= usuario.getNombre() %></strong></p>
    <% } else { %>
        <p>An error occurred while retrieving user data. Please log in again.</p>
    <% } %>

     <form action="<%= request.getContextPath() %>/jsp/index.jsp" method="get" style="margin-top: 20px;">
            <button type="submit">Go back to the homepage</button>
     </form>

    <!-- Botón de logout -->
    <% if (usuario != null) { %>
        <form action="<%= request.getContextPath() %>/control" method="post" style="margin-top: 20px;">
            <input type="hidden" name="action" value="logout">
            <button type="submit">Log out</button>
        </form>
    <% } %>
</body>
</html>
