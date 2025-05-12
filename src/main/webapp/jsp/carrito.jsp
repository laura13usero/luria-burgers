<%@ page import="model.Hamburguesa" %>
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
                <strong><%= p.getNombre() %></strong> - $<%= p.getPrecio() %>
                <form action="<%= request.getContextPath() %>/control" method="get" style="display:inline;">
                    <input type="hidden" name="action" value="eliminarProductoCarrito">
                    <input type="hidden" name="index" value="<%= i %>">
                    <button type="submit">Eliminar</button>
                </form>
            </li>
        <% } %>
    </ul>
    <p><strong>Total a pagar:</strong> $<%= df.format(total) %></p>
<% } %>

<form action="<%= request.getContextPath() %>/jsp/index.jsp" method="get">
    <button type="submit">Seguir comprando</button>
</form>

<!-- PayPal SDK con tu Client ID (SANDBOX por ahora) -->
<script src="https://www.paypal.com/sdk/js?client-id=TU_CLIENT_ID_SANDBOX&currency=EUR"></script>

<div id="paypal-button-container"></div>

<script>
paypal.Buttons({
    createOrder: function(data, actions) {
        return actions.order.create({
            purchase_units: [{
                amount: {
                    value: 'TOTAL_DEL_CARRITO' // Sustituye con el total real desde el backend (puedes meterlo con EL o JavaScript)
                }]
            }]
        });
    },
    onApprove: function(data, actions) {
        return actions.order.capture().then(function(details) {
            // Envía los detalles al servlet para registrar el pago
            return fetch('FrontController?action=RegistrarPago', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(details)
            })
            .then(response => response.text())
            .then(result => {
                alert("¡Pago completado! Redirigiendo al resumen...");
                window.location.href = "FrontController?action=VerResumenPedido";
            });
        });
    }
}).render('#paypal-button-container');
</script>



</body>
</html>
