<%@ page import="model.Producto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>

<%
List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
double total = 0.0;
if (request.getAttribute("total") != null) {
    total = (double) request.getAttribute("total");
}
DecimalFormat df = new DecimalFormat("#0.00");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Carrito de Compras</title>
</head>
<body>

<h1>Tu carrito</h1>

<% if (carrito == null || carrito.isEmpty()) { %>
    <p>El carrito está vacío.</p>
<% } else { %>
    <ul>
        <% for (int i = 0; i < carrito.size(); i++) {
            Producto p = carrito.get(i); %>
            <li>
                <strong><%= p.getNombre() %></strong> - \$<%= p.getPrecio() %>
                <form action="<%= request.getContextPath() %>/control" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="actualizarCantidad">
                    <input type="hidden" name="index" value="<%= i %>">
                    <button type="submit" name="operation" value="decrement">−</button>
                    <span><%= session.getAttribute("cantidad_" + p.getId()) %></span>
                    <button type="submit" name="operation" value="increment">+</button>
                </form>
                <form action="<%= request.getContextPath() %>/control" method="get" style="display:inline;">
                    <input type="hidden" name="action" value="eliminarProductoCarrito">
                    <input type="hidden" name="index" value="<%= i %>">
                    <button type="submit">Eliminar</button>
                </form>
            </li>
        <% } %>
    </ul>
    <p><strong>Total a pagar:</strong> \$<%= df.format(total) %></p>
<% } %>

<form action="<%= request.getContextPath() %>/jsp/index.jsp" method="get">
    <button type="submit">Seguir comprando</button>
</form>

</body>
</html>
