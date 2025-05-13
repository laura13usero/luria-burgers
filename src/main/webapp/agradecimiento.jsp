<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mensaje Enviado</title>
</head>
<body>
    <h2>¡Gracias!</h2>
    <p>Tu mensaje ha sido enviado al comité de forma confidencial.</p>
    <form action="<%= request.getContextPath() %>/jsp/empleadointranet.jsp" method="get" style="margin-top: 20px;">
            <button type="submit">Volver a la Intranet</button>
    </form>
</body>
</html>
