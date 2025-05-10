<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Producto" %>
<%@ page import="model.Usuario" %>

<%
    Usuario usuario = null;
    if (session != null) {
        usuario = (Usuario) session.getAttribute("usuarioLogueado");
    }
    List<Producto> hamburguesas = (List<Producto>) request.getAttribute("hamburguesas");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Hamburguesas</title>
</head>
<body>

    <h1>Lista de Hamburguesas</h1>

    <% if (usuario != null) { %>
        <p>Hola, <strong><%= usuario.getNombre() %></strong>. Estás logueado.</p>
    <% } else { %>
        <p>Bienvenido, por favor inicia sesión para acceder a más funcionalidades.</p>
    <% } %>

    <% if (hamburguesas != null && !hamburguesas.isEmpty()) { %>
        <ul>
            <% for (Producto hamburguesa : hamburguesas) { %>
                <li>
                    <strong><%= hamburguesa.getNombre() %></strong>
                    <% if (hamburguesa.getDescripcion() != null && !hamburguesa.getDescripcion().isEmpty()) { %>
                        - <%= hamburguesa.getDescripcion() %>
                    <% } %>
                    - $<%= hamburguesa.getPrecio() %>

                    <!-- Añadir al carrito -->
                    <form action="<%= request.getContextPath() %>/control" method="post" style="margin-top: 10px;">
                        <input type="hidden" name="action" value="addToCart">
                        <input type="hidden" name="idProducto" value="<%= hamburguesa.getId() %>">
                        <input type="hidden" name="tipoProducto" value="hamburguesa">
                        <button type="submit">Añadir al carrito</button>
                    </form>
                </li>
            <% } %>
        </ul>
    <% } else { %>
        <p>No hay hamburguesas disponibles.</p>
    <% } %>

    <form action="<%= request.getContextPath() %>/jsp/index.jsp" method="get" style="margin-top: 20px;">
        <button type="submit">Volver a la página principal</button>
    </form>

    <!-- Botón de ver carrito (delegado al backend) -->
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
