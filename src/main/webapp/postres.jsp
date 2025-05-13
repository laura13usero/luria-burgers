<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Postres</title>
    <script>
        async function obtenerPostres() {
            try {
                const response = await fetch("/webapp-1.0-SNAPSHOT/control?action=getPostresJSON");
                const data = await response.json();
                const contenedor = document.getElementById('postres-list');
                contenedor.innerHTML = '';

                if (data.length > 0) {
                    data.forEach(postre => {
                        const li = document.createElement('li');
                        li.innerHTML = `
                            <strong>${postre.nombre}</strong>
                            ${postre.descripcion ? ' - ' + postre.descripcion : ''}
                            <form action="/webapp-1.0-SNAPSHOT/control" method="post" style="margin-top: 10px;">
                                <input type="hidden" name="action" value="addToCart">
                                <input type="hidden" name="idProducto" value="${postre.id}">
                                <input type="hidden" name="tipoProducto" value="postre">
                                <button type="submit">Add to cart for: $${postre.precio}</button>
                            </form>
                        `;
                        contenedor.appendChild(li);
                    });
                } else {
                    contenedor.innerHTML = '<p>No postres disponibles.</p>';
                }
            } catch (error) {
                console.error("Error al obtener postres:", error);
            }
        }

        document.addEventListener('DOMContentLoaded', obtenerPostres);
    </script>
</head>
<body>

<h1>Postres</h1>

<ul id="postres-list"></ul>

<!-- Botones inferiores -->
<button onclick="window.location.href = 'index.html'" style="margin-top: 20px;">Return to Home Page</button>
<button onclick="window.location.href = 'carrito.html'" style="margin-top: 10px;">Cart</button>

</body>
</html>
