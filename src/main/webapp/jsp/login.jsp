<!-- src/main/webapp/login.html (o login.jsp si prefieres) -->
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Login</h2>

    <label>E-mail: <input type="email" id="email" required></label><br>
    <label>Password: <input type="password" id="contrasena" required></label><br>
    <button onclick="login()">Login</button>

    <p id="error" style="color: red;"></p>

    <script>
        async function login() {
            const email = document.getElementById("email").value;
            const contrasena = document.getElementById("contrasena").value;
            const errorElem = document.getElementById("error");
            errorElem.textContent = ""; // limpia errores anteriores

            const response = await fetch("/webapp-1.0-SNAPSHOT/control?action=login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ email, contrasena })
            });

            const result = await response.json();

            if (result.status === "ok") {
                if (result.rol === "admin") {
                    window.location.href = "/webapp-1.0-SNAPSHOT/jsp/adminintranet.jsp";
                } else if (result.rol === "empleado") {
                    window.location.href = "/webapp-1.0-SNAPSHOT/jsp/empleadointranet.jsp";
                } else {
                    window.location.href = "/webapp-1.0-SNAPSHOT/jsp/loginExitoso.jsp";
                }
            } else {
                errorElem.textContent = result.message;
            }
        }
    </script>
</body>
</html>
