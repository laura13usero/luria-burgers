<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Producto" %>
<%@ page import="model.Usuario" %>
<%@ page import="dao.HamburguesaDAO" %>

<%
    Usuario usuario = null;
    if (session != null) {
        usuario = (Usuario) session.getAttribute("usuarioLogueado");
    }
    List<Producto> hamburguesas = (List<Producto>) request.getAttribute("hamburguesas");
    HamburguesaDAO hamburguesaDAO = new HamburguesaDAO();
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Hamburguesas</title>
</head>
<body>

<h1>Burgers</h1>

<% if (usuario != null) { %>
    <p>こんにちは, <strong><%= usuario.getNombre() %></strong>. You are logged in.</p>
<% } else { %>
    <p>Welcome! Please log in to access more features.</p>
<% } %>

<% if (hamburguesas != null && !hamburguesas.isEmpty()) { %>
    <ul>
        <% for (Producto hamburguesa : hamburguesas) { %>
            <li>
                <strong><%= hamburguesa.getNombre() %></strong>
                <% if (hamburguesa.getDescripcion() != null && !hamburguesa.getDescripcion().isEmpty()) { %>
                    - <%= hamburguesa.getDescripcion() %>
                <% } %>

                <!-- VOTACIÓN 🌺 -->
                <%
                    boolean yaVoto = false;
                    if (usuario != null) {
                        String usuarioVotado = String.valueOf(usuario.getId());
                        List<String> ranking = hamburguesa.getRanking(); // lista de IDs
                        yaVoto = ranking.contains(usuarioVotado);
                    }
                %>
                <span>
                    <% if (yaVoto) { %>
                        🌺
                    <% } else { %>
                        🌸
                    <% } %>
                </span>
                <span> Votos: <%= hamburguesaDAO.obtenerCantidadVotos(hamburguesa.getId()) %></span>

                <% if (usuario != null && !yaVoto) { %>
                    <form action="<%= request.getContextPath() %>/control" method="post" style="margin-top: 5px;">
                        <input type="hidden" name="action" value="votarHamburguesa">
                        <input type="hidden" name="idProducto" value="<%= hamburguesa.getId() %>">
                        <button type="submit">Vote</button>
                    </form>
                <% } %>

                <!-- Add to cart -->
                <form action="<%= request.getContextPath() %>/control" method="post" style="margin-top: 10px;">
                    <input type="hidden" name="action" value="addToCart">
                    <input type="hidden" name="idProducto" value="<%= hamburguesa.getId() %>">
                    <input type="hidden" name="tipoProducto" value="hamburguesa">
                    <button type="submit">Add to cart for: $<%= hamburguesa.getPrecio() %></button>
                </form>
            </li>
        <% } %>
    </ul>
<% } else { %>
    <p>No burgers available.</p>
<% } %>

<h2>Filter burgers by type:</h2>
<form action="<%= request.getContextPath() %>/control" method="get" style="margin-bottom: 20px;">
    <input type="hidden" name="action" value="filtrarHamburguesas" />
    <button type="submit" name="filtro" value="premium"> Premium</button>
    <button type="submit" name="filtro" value="spicy"> Spicy</button>
    <button type="submit" name="filtro" value="chicken"> Chicken</button>
    <button type="submit" name="filtro" value="">All Burgers</button>
</form>

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
