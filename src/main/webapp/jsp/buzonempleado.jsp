<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Buzón del Empleado</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 2em;
        }
        form {
            max-width: 600px;
            margin: auto;
            background-color: #f5f5f5;
            padding: 2em;
            border-radius: 10px;
        }
        textarea, select, button {
            width: 100%;
            margin-top: 1em;
            padding: 0.8em;
            font-size: 1em;
        }
        button {
            background-color: #0077cc;
            color: white;
            border: none;
            cursor: pointer;
        }
        button:hover {
            background-color: #005fa3;
        }
    </style>
</head>
<body>

    <h2>Buzón del Empleado</h2>
    <p style="text-align:center;">Tu mensaje será enviado de forma confidencial y anónima al comité.</p>

    <form action="<%= request.getContextPath() %>/FrontController" method="post">
        <label for="tipo">Tipo de mensaje:</label>
        <select name="tipo" id="tipo" required>
            <option value="consulta">Consulta</option>
            <option value="queja">Queja</option>
            <option value="propuesta">Propuesta</option>
        </select>

        <label for="mensaje">Mensaje:</label>
        <textarea name="mensaje" id="mensaje" rows="8" placeholder="Escribe tu mensaje aquí..." required></textarea>

        <input type="hidden" name="action" value="EnviarMensajeAnonimo">
        <button type="submit">Enviar</button>
    </form>

</body>
</html>
