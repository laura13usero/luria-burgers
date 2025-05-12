<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true" %>
<%
    if (!"empleado".equals(session.getAttribute("rol"))) {
        response.sendRedirect(request.getContextPath() + "/jsp/accesoDenegado.jsp");
        return;
    }
%>
<html>
<head><title>Zona Personal</title></head>
<body>
<h2>🧍 Zona Personal</h2>

<form action="emplecomite.jsp"><button type="submit">Comité de Empresa</button></form>
<form action="buzonempleado.jsp"><button type="submit">Buzón de Sugerencias</button></form>
<form action="emplecalendario.jsp"><button type="submit">Calendario Laboral</button></form>
<form action="emplenormativa.jsp"><button type="submit">Normativa y Convenio</button></form>
<form action="emplenoticias.jsp"><button type="submit">Noticias y Comunicados</button></form>
<form action="empleciberseguridad.jsp"><button type="submit">IT y Ciberseguridad</button></form>
<form action="emplecultura.jsp"><button type="submit">Bienestar y Cultura</button></form>
<form action="emplsostenibilidad.jsp"><button type="submit">Sostenibilidad y RSC</button></form>

<a href="empleadointranet.jsp">🔙 Volver</a>
</body>
</html>
