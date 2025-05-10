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
    <title>Login Exitoso</title>
</head>
<body>
    <h1>¡Login Exitoso!</h1>

    <% if (usuario != null) { %>
        <p>Bienvenido, <strong><%= usuario.getNombre() %></strong></p>
    <% } else { %>
        <p>Ha ocurrido un error al obtener los datos del usuario. Por favor, vuelve a iniciar sesión.</p>
    <% } %>

     <form action="<%= request.getContextPath() %>/jsp/index.jsp" method="get" style="margin-top: 20px;">
            <button type="submit">Volver a la página principal</button>
     </form>

    <!-- Botón de logout -->
    <% if (usuario != null) { %>
        <form action="<%= request.getContextPath() %>/control" method="post" style="margin-top: 20px;">
            <input type="hidden" name="action" value="logout">
            <button type="submit">Cerrar sesión</button>
        </form>
    <% } %>
</body>
</html>
