<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Producto" %>
<%@ page import="model.Usuario" %>
<%@ page import="java.util.List" %>

<%
    Usuario usuario = null;
    if (session != null) {
        usuario = (Usuario) session.getAttribute("usuarioLogueado");
    }
    List<Producto> guarniciones = (List<Producto>) request.getAttribute("guarniciones");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Guarniciones</title>
</head>
<body>

    <h1>Lista de Guarniciones</h1>

    <% if (usuario != null) { %>
        <p>Hola, <strong><%= usuario.getNombre() %></strong>. Estás logueado.</p>
    <% } else { %>
        <p>Bienvenido, por favor inicia sesión para acceder a más funcionalidades.</p>
    <% } %>

    <% if (guarniciones != null && !guarniciones.isEmpty()) { %>
        <ul>
            <% for (Producto guarnicion : guarniciones) { %>
                <li>
                    <strong><%= guarnicion.getNombre() %></strong>
                    <% if (guarnicion.getDescripcion() != null && !guarnicion.getDescripcion().isEmpty()) { %>
                        - <%= guarnicion.getDescripcion() %>
                    <% } %>
                    - $<%= guarnicion.getPrecio() %>

                    <!-- Añadir al carrito -->
                    <form action="<%= request.getContextPath() %>/control" method="post" style="margin-top: 10px;">
                        <input type="hidden" name="action" value="addToCart">
                        <input type="hidden" name="idProducto" value="<%= guarnicion.getId() %>">
                        <button type="submit">Añadir al carrito</button>
                    </form>
                </li>
            <% } %>
        </ul>
    <% } else { %>
        <p>No hay guarniciones disponibles.</p>
    <% } %>

    <form action="<%= request.getContextPath() %>/jsp/index.jsp" method="get" style="margin-top: 20px;">
        <button type="submit">Volver a la página principal</button>
    </form>

    <!-- Botón de ver carrito -->
    <form action="<%= request.getContextPath() %>/control" method="get" style="margin-top: 10px;">
        <input type="hidden" name="action" value="verCarrito">
        <button type="submit">Ver mi carrito</button>
    </form>

    <% if (usuario != null) { %>
        <form action="<%= request.getContextPath() %>/control" method="post" style="margin-top: 20px;">
            <input type="hidden" name="action" value="logout">
            <button type="submit">Cerrar sesión</button>
        </form>
    <% } %>

</body>
</html>
