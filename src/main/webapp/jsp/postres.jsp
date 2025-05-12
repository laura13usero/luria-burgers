<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Producto" %>
<%@ page import="model.Usuario" %>
<%@ page import="java.util.List" %>

<%
    Usuario usuario = null;
    if (session != null) {
        usuario = (Usuario) session.getAttribute("usuarioLogueado");
    }
    List<Producto> postres = (List<Producto>) request.getAttribute("postres");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Postres</title>
</head>
<body>

    <h1>Dessert</h1>

    <% if (usuario != null) { %>
        <p>こんにちは, <strong><%= usuario.getNombre() %></strong>. You are logged in!.</p>
    <% } else { %>
        <p>Welcome! Please log in to access more features.</p>
    <% } %>

    <% if (postres != null && !postres.isEmpty()) { %>
        <ul>
            <% for (Producto postre : postres) { %>
                <li>
                    <strong><%= postre.getNombre() %></strong>
                    <% if (postre.getDescripcion() != null && !postre.getDescripcion().isEmpty()) { %>
                        - <%= postre.getDescripcion() %>
                    <% } %>

                    <!-- El precio se muestra solo en el botón, no aquí -->
                    <form action="<%= request.getContextPath() %>/control" method="post" style="margin-top: 10px;">
                        <input type="hidden" name="action" value="addToCart">
                        <input type="hidden" name="idProducto" value="<%= postre.getId() %>">
                        <button type="submit">Add to cart for: - $<%= postre.getPrecio() %></button>
                    </form>
                </li>
            <% } %>
        </ul>
    <% } else { %>
        <p>No hay postres disponibles.</p>
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
