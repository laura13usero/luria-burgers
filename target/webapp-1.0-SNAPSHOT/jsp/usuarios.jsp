<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Usuario"%>
<html>
<head>
    <title>Usuarios</title>
</head>
<body>
    <h1>Lista de Usuarios</h1>

    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Email</th>
                <th>Teléfono</th>
                <th>Dirección</th>
                <th>Fecha de Registro</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
                for (Usuario usuario : usuarios) {
            %>
            <tr>
                <td><%= usuario.getIdUsuario() %></td>
                <td><%= usuario.getNombre() %></td>
                <td><%= usuario.getEmail() %></td>
                <td><%= usuario.getTelefono() %></td>
                <td><%= usuario.getDireccion() %></td>
                <td><%= usuario.getFechaRegistro() %></td>
            </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
