<%@ page import="model.Producto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>

<%
    List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
    double total = 0.0;
    DecimalFormat df = new DecimalFormat("#0.00");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Resumen de Compra</title>
</head>
<body>

<h1>Resumen de tu compra</h1>

<% if (carrito == null || carrito.isEmpty()) { %>
    <p>Tu carrito está vacío.</p>
<% } else { %>
    <table border="1" cellpadding="5">
        <tr>
            <th>Producto</th>
            <th>Precio</th>
        </tr>
        <%
            for (Producto p : carrito) {
                total += p.getPrecio();
        %>
            <tr>
                <td><%= p.getNombre() %></td>
                <td>$<%= df.format(p.getPrecio()) %></td>
            </tr>
        <% } %>
        <tr>
            <td><strong>Total a pagar</strong></td>
            <td><strong>$<%= df.format(total) %></strong></td>
        </tr>
    </table>

    <!-- Botón de PayPal -->
    <div id="paypal-button-container" style="margin-top: 20px;"></div>

    <script src="https://www.paypal.com/sdk/js?client-id=AaDAT1lIR-rPp2H1RFiSd9xPbD1me0OvaZQy3WzYN2Bh5P2xsp9MqAKd1hApRESxjdRM5zuvc-_zrkqA&currency=EUR"></script>
    <script>
        paypal.Buttons({
            createOrder: function(data, actions) {
                return actions.order.create({
                    purchase_units: [{
                        amount: {
                            value: '<%= df.format(total) %>'
                        }
                    }]
                });
            },
            onApprove: function(data, actions) {
                return actions.order.capture().then(function(details) {
                    alert('Pago completado por ' + details.payer.name.given_name);
                    // Redirigir al backend para finalizar pedido
                    window.location.href = '<%= request.getContextPath() %>/control?action=finalizarCompra';
                });
            }
        }).render('#paypal-button-container');
    </script>
<% } %>

<form action="<%= request.getContextPath() %>/jsp/carrito.jsp" method="get" style="margin-top: 20px;">
    <button type="submit">Volver al carrito</button>
</form>

</body>
</html>
