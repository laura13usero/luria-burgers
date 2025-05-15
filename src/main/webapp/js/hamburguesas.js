let activeFilter = '';
let votosUsuario = {};
let usuarioActual = null;
let currentRatings = {}; // Objeto para almacenar los ratings temporales

async function obtenerHamburguesas(filtro = '') {
    activeFilter = filtro;
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

                let rankingHTML = `
                    <div class="ranking-container" data-producto-id="${hamburguesa.id}">
                        <button class="rating-button minus" onclick="cambiarRating(${hamburguesa.id}, -1)">-</button>
                        <img src="assets/fondos_recursos/flor.png" alt="Calificación 1" class="rating-icon" data-rating="1">
                        <img src="assets/fondos_recursos/flor-apagada.png" alt="Calificación 2" class="rating-icon" data-rating="2">
                        <img src="assets/fondos_recursos/flor-apagada.png" alt="Calificación 3" class="rating-icon" data-rating="3">
                        <img src="assets/fondos_recursos/flor-apagada.png" alt="Calificación 4" class="rating-icon" data-rating="4">
                        <img src="assets/fondos_recursos/flor-apagada.png" alt="Calificación 5" class="rating-icon" data-rating="5">
                        <button class="rating-button plus" onclick="cambiarRating(${hamburguesa.id}, 1)">+</button>
                        <button class="send-rating-button" onclick="enviarCalificacion(${hamburguesa.id})">Send rate</button>
                    </div>
                `;

                card.innerHTML = `
                    <img src="${imagenSrc}" alt="${hamburguesa.nombre}">
                    <div class="burger-info">
                        <h2>${hamburguesa.nombre}</h2>
                        <p class="precio">S/ ${hamburguesa.precio.toFixed(2)}</p>
                        ${rankingHTML}
                        <button class="add-to-cart-button" onclick="agregarAlCarrito(${hamburguesa.id}, 'hamburguesa')">
                            Add to cart - S/ ${hamburguesa.precio.toFixed(2)}
                        </button>
                    </div>
                `;
                container.appendChild(card);
                currentRatings[hamburguesa.id] = 1; // Inicializar el rating actual
                actualizarRankingVisual(hamburguesa, usuarioActual);
            });
        } else {
            container.innerHTML = '<p>No se encontraron hamburguesas.</p>';
        }
    } catch (error) {
        console.error('Error al obtener las hamburguesas:', error);
        document.querySelector('.cards-container').innerHTML = '<p>Error al cargar las hamburguesas.</p>';
    }
}

function cambiarRating(idHamburguesa, cambio) {
    if (!usuarioActual) {
        alert("Debes iniciar sesión para calificar.");
        return;
    }

    let rating = currentRatings[idHamburguesa] || 1; // Obtener el rating actual o inicializar en 1
    rating += cambio;

    if (rating < 1) rating = 1;
    if (rating > 5) rating = 5;

    currentRatings[idHamburguesa] = rating;
    actualizarRankingVisual({ id: idHamburguesa }, usuarioActual, rating); // Pasar el rating actual
    document.getElementById(`rating-${idHamburguesa}`).textContent = rating;
}

async function enviarCalificacion(idHamburguesa) {
    if (!usuarioActual) {
        alert("Debes iniciar sesión para calificar.");
        return;
    }

    if (haVotado(usuarioActual, idHamburguesa)) {
        alert("Ya has calificado esta hamburguesa.");
        return;
    }

    const rating = currentRatings[idHamburguesa];

    try {
        const response = await fetch('control?action=actualizarRanking', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                idProducto: idHamburguesa,
                usuario: usuarioActual,
                rating: rating
            })
        });

        const data = await response.json();

        if (data.status === "ok") {
            alert("¡Gracias por tu calificación!");
            votosUsuario[usuarioActual] = votosUsuario[usuarioActual] || {};
            votosUsuario[usuarioActual][idHamburguesa] = true;
            obtenerHamburguesas(activeFilter);
        } else {
            alert("Error al calificar. Inténtalo de nuevo.");
        }

    } catch (error) {
        console.error('Error al enviar la calificación:', error);
        alert("Error al procesar la calificación. Inténtalo de nuevo.");
    }
}

async function obtenerUsuarioLogueado() {
    try {
        const response = await fetch('control?action=getUsuarioLogueado');
        const data = await response.json();
        if (data.status === 'ok') {
            usuarioActual = data.nombre;  //  O data.email, o lo que necesites
            // Si envías más datos del usuario, puedes acceder a ellos aquí
            // Por ejemplo:  usuarioActual = data.usuario.email;
        } else {
            usuarioActual = null;
        }
    } catch (error) {
        console.error('Error al obtener el usuario logueado:', error);
        usuarioActual = null;
    }
}

function haVotado(usuario, idProducto) {
    return votosUsuario[usuario] && votosUsuario[usuario][idProducto];
}

document.addEventListener('DOMContentLoaded', async () => {
    await obtenerUsuarioLogueado(); // Obtener el usuario al cargar la página
    obtenerHamburguesas();
    console.log("Usuario actual (DOMContentLoaded):", usuarioActual);
});

function actualizarRankingVisual(hamburguesa, usuarioActual, currentRating) {
    const rankingContainer = document.querySelector(`.ranking-container[data-producto-id="${hamburguesa.id}"]`);
    if (!rankingContainer) return;

    const iconosRating = rankingContainer.querySelectorAll('.rating-icon');
    iconosRating.forEach(icon => {
        icon.src = "assets/fondos_recursos/flor-apagada.png"; // Resetea
    });

    let rating = currentRating || Math.round(hamburguesa.promedioRanking) || 1;
    for (let i = 0; i < rating; i++) {
        if (iconosRating[i]) {
            iconosRating[i].src = "assets/fondos_recursos/flor.png";
        }
    }

    if (usuarioActual && hamburguesa.ranking && hamburguesa.ranking.includes(usuarioActual)) {
        // Si el usuario ya votó, podrías deshabilitar los botones
        const botones = rankingContainer.querySelectorAll('.rating-button');
        botones.forEach(boton => {
            boton.disabled = true;
        });
        const sendButton = rankingContainer.querySelector('.send-rating-button');
        if (sendButton) {
            sendButton.disabled = true;
        }
    }
}