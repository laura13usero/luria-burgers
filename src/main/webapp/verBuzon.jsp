<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%
    String rol = (String) session.getAttribute("rol");
    if (!"admin".equals(rol)) {
        response.sendRedirect(request.getContextPath() + "/jsp/accesoDenegado.jsp");
        return;
    }
%>
<html>
<head>
    <title>Mensajes del Buzón - Administrador</title>
</head>
<body>
    <h1>Mensajes del Buzón</h1>

    <table border="1" style="width: 100%; margin-top: 20px; text-align: left;">
        <tr>
            <th>Tipo</th>
            <th>Mensaje</th>
            <th>Fecha</th>
        </tr>

        <%
            List<String[]> mensajes = (List<String[]>) request.getAttribute("mensajes");
            if (mensajes != null && !mensajes.isEmpty()) {
                for (String[] mensaje : mensajes) {
        %>
            <tr>
                <td><%= mensaje[0] %></td>
                <td><%= mensaje[1] %></td>
                <td><%= mensaje[2] %></td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="3">No hay mensajes en el buzón.</td>
            </tr>
        <%
            }
        %>
    </table>

    <br>
    <form action="<%= request.getContextPath() %>/jsp/adminintranet.jsp" method="get" style="margin-top: 20px;">
            <button type="submit">Volver al panel de administración</button>
    </form>
</body>
</html>
