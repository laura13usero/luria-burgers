<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Drinks</title>
    <script>
        async function obtenerBebidas() {
            try {
                const response = await fetch("/webapp-1.0-SNAPSHOT/control?action=getBebidasJSON");

                // Verificamos si la respuesta es correcta
                if (!response.ok) {
                    throw new Error('Error en la respuesta');
                }

                const data = await response.json();

                // Obtenemos el contenedor donde se mostrarán las bebidas
                const contenedor = document.getElementById('bebidas-list');
                contenedor.innerHTML = ''; // Limpiamos el contenedor

                // Si hay bebidas, las mostramos
                if (data.length > 0) {
                    data.forEach(bebida => {
                        const li = document.createElement('li');
                        li.innerHTML = `
                            <strong>${bebida.nombre}</strong>
                            ${bebida.descripcion ? ' - ' + bebida.descripcion : ''}
                            <form action="/webapp-1.0-SNAPSHOT/control" method="post" style="margin-top: 10px;">
                                <input type="hidden" name="action" value="addToCart">
                                <input type="hidden" name="idProducto" value="${bebida.id}">
                                <input type="hidden" name="tipoProducto" value="bebida">
                                <button type="submit">Add to cart for: $${bebida.precio}</button>
                            </form>
                        `;
                        contenedor.appendChild(li);
                    });
                } else {
                    contenedor.innerHTML = '<p>No drinks available.</p>';
                }

            } catch (error) {
                console.error("Error al obtener bebidas:", error);
            }
        }

        // Llamar a la función cuando la página esté lista
        document.addEventListener('DOMContentLoaded', obtenerBebidas);
    </script>
</head>
<body>

<h1>Drinks</h1>

<ul id="bebidas-list"></ul>

<!-- Botones inferiores -->
<button onclick="window.location.href = 'index.html'" style="margin-top: 20px;">Return to Home Page</button>
<button onclick="window.location.href = 'carrito.html'" style="margin-top: 10px;">Cart</button>

</body>
</html>
