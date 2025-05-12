<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Usuario" %>
<html>
<head>
    <title>Registrar Empleado</title>
</head>
<body>

<h2>Registrar nuevo empleado</h2>

<% if ("ok".equals(request.getAttribute("status"))) { %>
    <p style="color: green;">✅ Registro de empleado completado</p>
<% } else if ("baja-ok".equals(request.getAttribute("status"))) { %>
    <p style="color: green;">✅ Empleado dado de baja correctamente</p>
<% } else if ("baja-error".equals(request.getAttribute("status"))) { %>
    <p style="color: red;">❌ Error al dar de baja al empleado</p>
<% } %>

<form action="${pageContext.request.contextPath}/control" method="post">
    <input type="hidden" name="action" value="empleado-register">
    Nombre: <input type="text" name="nombre" required><br>
    Email: <input type="email" name="email" required><br>
    Contraseña: <input type="password" name="contrasena" required><br>
    Teléfono: <input type="text" name="telefono"><br>
    Dirección: <input type="text" name="direccion"><br>
    <button type="submit">Registrar</button>
</form>

<%
    List<Usuario> empleados = (List<Usuario>) request.getAttribute("empleados");
    if (empleados != null && !empleados.isEmpty()) {
%>
    <h3>Empleados actuales</h3>
    <table border="1">
        <tr>
            <th>ID</th><th>Nombre</th><th>Email</th><th>Teléfono</th><th>Dirección</th><th>Fecha Registro</th><th>Acciones</th>
        </tr>
        <% for (Usuario u : empleados) { %>
        <tr>
            <td><%= u.getIdUsuario() %></td>
            <td><%= u.getNombre() %></td>
            <td><%= u.getEmail() %></td>
            <td><%= u.getTelefono() %></td>
            <td><%= u.getDireccion() %></td>
            <td><%= u.getFechaRegistro() %></td>
            <td>
                <a href="${pageContext.request.contextPath}/control?action=empleado-baja&id=<%= u.getIdUsuario() %>">Dar de baja</a>
            </td>
        </tr>
        <% } %>
    </table>
<% } else { %>
    <p>No hay empleados registrados todavía.</p>
<% } %>

<form action="<%= request.getContextPath() %>/jsp/adminintranet.jsp" method="get" style="margin-top: 20px;">
    <button type="submit">Volver al panel de administración</button>
</form>

</body>
</html>

