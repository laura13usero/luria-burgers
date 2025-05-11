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
            <th>ID</th><th>Nombre</th><th>Email</th><th>Teléfono</th><th>Dirección</th><th>Fecha Registro</th>
        </tr>
        <% for (Usuario u : empleados) { %>
        <tr>
            <td><%= u.getId() %></td>
            <td><%= u.getNombre() %></td>
            <td><%= u.getEmail() %></td>
            <td><%= u.getTelefono() %></td>
            <td><%= u.getDireccion() %></td>
            <td><%= u.getFechaRegistro() %></td>
        </tr>
        <% } %>
    </table>
<% } else { %>
    <p>No hay empleados registrados todavía.</p>
<% } %>

</body>
</html>
