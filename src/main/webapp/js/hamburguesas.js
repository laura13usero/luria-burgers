let activeFilter = ''; // Variable para almacenar el filtro activo
let votosUsuario = {};  // Objeto para controlar los votos del usuario
let usuarioActual = null; // Variable para almacenar el usuario actual

async function obtenerHamburguesas(filtro = '') {
    activeFilter = filtro; // Actualiza el filtro activo
    document.querySelectorAll('.filter-button').forEach(button => {
        button.classList.remove('active');
        if (button.dataset.filter === filtro) {
            button.classList.add('active');
        }
    });

    try {
        const url = filtro
            ? `control?action=filtrarHamburguesas&filtro=${filtro}`
            : `control?action=getHamburguesasJSON`;
        const response = await fetch(url);
        const data = await response.json();
        const container = document.querySelector('.cards-container');
        container.innerHTML = '';

        if (data.length > 0) {
            data.forEach(hamburguesa => {
                const card = document.createElement('div');
                card.classList.add('card');

                const imagenSrc = `assets/menu/cards_hamburguesas_animacion/${hamburguesa.imagen_png}`;
                console.log("Intentando cargar imagen desde:", imagenSrc);

                const rankingHTML = `
                    <div class="ranking-container" data-producto-id="${hamburguesa.id}">
                        <img src="assets/fondos_recursos/flor-apagada.png" alt="Sin calificar" class="rating-icon" data-rating="1" onclick="calificarHamburguesa(${hamburguesa.id}, 1)">
                        <img src="assets/fondos_recursos/flor-apagada.png" alt="Sin calificar" class="rating-icon" data-rating="2" onclick="calificarHamburguesa(${hamburguesa.id}, 2)">
                        <img src="assets/fondos_recursos/flor-apagada.png" alt="Sin calificar" class="rating-icon" data-rating="3" onclick="calificarHamburguesa(${hamburguesa.id}, 3)">
                        <img src="assets/fondos_recursos/flor-apagada.png" alt="Sin calificar" class="rating-icon" data-rating="4" onclick="calificarHamburguesa(${hamburguesa.id}, 4)">
                        <img src="assets/fondos_recursos/flor-apagada.png" alt="Sin calificar" class="rating-icon" data-rating="5" onclick="calificarHamburguesa(${hamburguesa.id}, 5)">
                    </div>
                `;

                card.innerHTML = `
                    <div class="card-img-container">
                        <img src="${imagenSrc}" alt="${hamburguesa.nombre}" class="burger-image">
                    </div>
                    <div class="card-info">
                        <h3>${hamburguesa.nombre}</h3>
                        <p>${hamburguesa.descripcion || ''}</p>
                        ${rankingHTML}
                        <button class="add-to-cart-button" onclick="agregarAlCarrito(${hamburguesa.id}, 'hamburguesa')">
                            Añadir al carrito - $${hamburguesa.precio}
                        </button>
                    </div>
                `;
                container.appendChild(card);
                mostrarPuntuacion(hamburguesa.id, hamburguesa.ranking);  // Mostrar puntuación inicial
            });
        } else {
            container.innerHTML = '<p>No hay hamburguesas disponibles con este filtro.</p>';
        }
    } catch (error) {
        console.error('Error al obtener hamburguesas:', error);
    }
}

async function agregarAlCarrito(idProducto, tipoProducto) {
    try {
        const response = await fetch(`control?action=addToCart&idProducto=${idProducto}`, {
            method: "POST"
        });
        const result = await response.json();
        if (result.status === "ok") {
            window.location.href = result.redirect;
        } else if (result.status === "login_required") {
            window.location.href = result.redirect;
        } else {
            alert("❌ Error al añadir al carrito: " + result.message);
        }
    } catch (error) {
        console.error("❌ Error de red al añadir al carrito:", error);
        alert("❌ Error inesperado.");
    }
}

function calcularPuntuacion(ranking) {
    if (!ranking || ranking.length === 0) return 0;

    let total = 0;
    for (let i = 0; i < ranking.length; i++) {
        total += parseInt(ranking[i].split('_')[1]); // Extraer la puntuación del string "usuario_puntuacion"
    }
    let promedio = total / ranking.length;

    if (promedio <= 2) return 1;
    if (promedio <= 4) return 2;
    if (promedio <= 6) return 3;
    if (promedio <= 8) return 4;
    return 5;
}

function mostrarPuntuacion(idProducto, ranking) {
    const contenedor = document.querySelector(`.ranking-container[data-producto-id="${idProducto}"]`);
    if (!contenedor) return;  // Si no se encuentra el contenedor, salir

    const puntuacion = calcularPuntuacion(ranking);
    const iconos = contenedor.querySelectorAll('.rating-icon');

    iconos.forEach(icon => {
        const rating = parseInt(icon.dataset.rating);
        if (rating <= puntuacion) {
            icon.src = 'assets/fondos_recursos/flor.png';  // Ruta a la imagen "activada"
            icon.alt = 'Calificado con ' + rating + ' estrellas';
        } else {
            icon.src = 'assets/fondos_recursos/flor-apagada.png';
            icon.alt = 'Sin calificar';
        }
    });
}

async function calificarHamburguesa(idHamburguesa, rating) {
    usuarioActual = obtenerUsuarioActual(); // Obtener el usuario al hacer clic

    if (!usuarioActual) {
        alert("Debes iniciar sesión para calificar.");
        return;
    }

    if (haVotado(usuarioActual, idHamburguesa)) {
        alert("Ya has calificado esta hamburguesa.");
        return;
    }

    try {
        const response = await fetch('control?action=ActualizarRanking', {  // Ajusta la URL si es necesario
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                idProducto: idHamburguesa,
                usuario: `${usuarioActual}_${rating}`  // Almacenar usuario y puntuación
            })
        });

        const data = await response.json();

        if (data.status === "ok") {
            alert("¡Gracias por tu calificación!");
            votosUsuario[usuarioActual] = votosUsuario[usuarioActual] || {};
            votosUsuario[usuarioActual][idHamburguesa] = true;  // Marcar como votado
            obtenerHamburguesas(activeFilter); // Recargar para mostrar la nueva puntuación
        } else {
            alert("Error al calificar. Inténtalo de nuevo.");
        }

    } catch (error) {
        console.error('Error al enviar la calificación:', error);
        alert("Error al procesar la calificación. Inténtalo de nuevo.");
    }
}

function obtenerUsuarioActual() {
    // Intenta obtener el usuario de la sesión (esto dependerá de cómo lo manejes en el backend)
    const usuario = sessionStorage.getItem('usuarioLogueado'); // O localStorage, según tu caso
    return usuario ? JSON.parse(usuario).email : null; // Devuelve el email del usuario o null si no hay sesión
}

function haVotado(usuario, idProducto) {
    return votosUsuario[usuario] && votosUsuario[usuario][idProducto];
}

document.addEventListener('DOMContentLoaded', () => {
    obtenerUsuarioActual(); // Obtener el usuario al cargar la página
    obtenerHamburguesas(); // Carga todas las hamburguesas al inicio
    console.log("Usuario actual:", usuarioActual);

});