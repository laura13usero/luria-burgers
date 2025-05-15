let activeFilter = ''; // Variable para almacenar el filtro activo
let votosUsuario = {};  // Objeto para controlar los votos del usuario

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
                        <button onclick="agregarAlCarrito(${hamburguesa.id}, 'hamburguesa')">
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
    const usuario = obtenerUsuarioActual(); // Función para obtener el usuario actual (implementar)

    if (!usuario) {
        alert("Debes iniciar sesión para calificar.");
        return;
    }

    if (haVotado(usuario, idHamburguesa)) {
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
                usuario: `${usuario}_${rating}`  // Almacenar usuario y puntuación
            })
        });

        const data = await response.json();

        if (data.status === "ok") {
            alert("¡Gracias por tu calificación!");
            votosUsuario[usuario] = votosUsuario[usuario] || {};
            votosUsuario[usuario][idHamburguesa] = true;  // Marcar como votado
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
    // Implementa esta función para obtener el usuario actual (desde la sesión, localStorage, etc.)
    // Por ejemplo, si tienes el nombre de usuario en localStorage:
    return localStorage.getItem('nombreUsuario');
    // Si usas sesiones, necesitarás una llamada al servidor para obtenerlo.
    // Si no tienes autenticación, podrías usar un prompt para simularlo (solo para desarrollo).
    // const usuario = prompt("Ingresa tu nombre de usuario:");
    // return usuario;
    return null; // Devuelve null si no hay usuario
}

function haVotado(usuario, idProducto) {
    // Verificar en el array de ranking si el usuario ya votó por este producto
    const contenedor = document.querySelector(`.ranking-container[data-producto-id="${idProducto}"]`);
    if (!contenedor) return false;
    const iconos = contenedor.querySelectorAll('.rating-icon');
    let yaVoto = false;
    iconos.forEach(icon => {
        if (icon.classList.contains('votado-' + usuario)) {
            yaVoto = true;
        }
    });
    return yaVoto;
}

document.addEventListener('DOMContentLoaded', () => {
    obtenerHamburguesas(); // Carga todas las hamburguesas al inicio
});