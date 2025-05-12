<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Producto" %>
<%@ page import="model.Usuario" %>
<%@ page import="java.util.List" %>

<%
    Usuario usuario = null;
    if (session != null) {
        usuario = (Usuario) session.getAttribute("usuarioLogueado");
    }
    List<Producto> bebidas = (List<Producto>) request.getAttribute("bebidas");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Drinks</title>
</head>
<body>

    <h1>Lista de Bebidas</h1>

    <% if (usuario != null) { %>
        <p>Hola, <strong><%= usuario.getNombre() %></strong>. Estás logueado.</p>
    <% } else { %>
        <p>Bienvenido, por favor inicia sesión para acceder a más funcionalidades.</p>
    <% } %>

    <% if (bebidas != null && !bebidas.isEmpty()) { %>
        <ul>
            <% for (Producto bebida : bebidas) { %>
                <li>
                    <strong><%= bebida.getNombre() %></strong>
                    <% if (bebida.getDescripcion() != null && !bebida.getDescripcion().isEmpty()) { %>
                        - <%= bebida.getDescripcion() %>
                    <% } %>

                    <!-- Aquí solo debe mostrar el nombre y descripción, no el precio -->
                    <form action="<%= request.getContextPath() %>/control" method="post" style="margin-top: 10px;">
                        <input type="hidden" name="action" value="addToCart">
                        <input type="hidden" name="idProducto" value="<%= bebida.getId() %>">
                        <button type="submit">Add to cart for: $<%= bebida.getPrecio() %></button>
                    </form>
                </li>
            <% } %>
        </ul>
    <% } else { %>
        <p>No hay bebidas disponibles.</p>
    <% } %>


    <form action="<%= request.getContextPath() %>/jsp/index.jsp" method="get" style="margin-top: 20px;">
        <button type="submit">Return to Home Page</button>
    </form>

    <!-- Botón de ver carrito -->
    <form action="<%= request.getContextPath() %>/control" method="get" style="margin-top: 10px;">
        <input type="hidden" name="action" value="verCarrito">
        <button type="submit">Cart</button>
    </form>

    <% if (usuario != null) { %>
        <form action="<%= request.getContextPath() %>/control" method="post" style="margin-top: 20px;">
            <input type="hidden" name="action" value="logout">
            <button type="submit">Log Out</button>
        </form>
    <% } %>

</body>
</html>
