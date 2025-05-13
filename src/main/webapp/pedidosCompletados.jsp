<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Compra" %>
<%@ page import="java.util.List" %>
<%@ page session="true" %>
<%
    String rol = (String) session.getAttribute("rol");
    if (!"admin".equals(rol)) {
        response.sendRedirect(request.getContextPath() + "/jsp/accesoDenegado.jsp");
        return;
    }
    List<Compra> pedidosCompletados = (List<Compra>) request.getAttribute("pedidosCompletados");
%>
<html>
<head>
    <title>Pedidos Completados</title>
</head>
<body>
    <h1>📦 Pedidos Completados</h1>

    <table border="1">
        <tr>
            <th>ID Compra</th>
            <th>ID Usuario</th>
            <th>Fecha y Hora</th>
            <th>Método de Pago</th>
            <th>Total (€)</th>
        </tr>
        <%
            for (Compra c : pedidosCompletados) {
        %>
        <tr>
            <td><%= c.getIdCompra() %></td>
            <td><%= c.getIdUsuario() %></td>
            <td><%= c.getFechaHora() %></td>
            <td><%= c.getMetodoPago() %></td>
            <td><%= c.getTotal() %></td>
        </tr>
        <%
            }
        %>
    </table>

    <form action="<%= request.getContextPath() %>/jsp/adminintranet.jsp" method="get" style="margin-top: 20px;">
        <button type="submit">Volver al panel de administración</button>
    </form>

</body>
</html>
