<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true" %>
<%
    if (!"empleado".equals(session.getAttribute("rol"))) {
        response.sendRedirect(request.getContextPath() + "/jsp/accesoDenegado.jsp");
        return;
    }
%>
<html>
<head><title>Zona Corporativa</title></head>
<body>
<h2>🏢 Zona Corporativa</h2>

<form action="emplenomina.jsp"><button type="submit">Mi Nómina</button></form>
<form action="emplecertificados.jsp"><button type="submit">Certificados y Trámites</button></form>
<form action="emplevacaciones.jsp"><button type="submit">Vacaciones y Permisos</button></form>
<form action="emplsalud.jsp"><button type="submit">Beneficios y Salud</button></form>
<form action="empledenuncias.jsp"><button type="submit">Canal de Denuncias</button></form>
<form action="empleriesgos.jsp"><button type="submit">Prevención de Riesgos Laborales</button></form>

<form action="<%= request.getContextPath() %>/jsp/empleadointranet.jsp" method="get" style="margin-top: 20px;">
        <button type="submit">Volver</button>
    </form>
</body>
</html>
