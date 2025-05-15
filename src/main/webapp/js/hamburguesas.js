let activeFilter = '';
let votosUsuario = {};
let usuarioActual = null;

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
                    <img src="${imagenSrc}" alt="${hamburguesa.nombre}">
                    <div class="burger-info">
                        <h2>${hamburguesa.nombre}</h2>
                        <p>${hamburguesa.descripcion}</p>
                        <p class="precio">S/ ${hamburguesa.precio.toFixed(2)}</p>
                        ${rankingHTML}
                        <a href="${hamburguesa.enlace_html}" class="order-btn">Ordenar</a>
                    </div>
                `;
                container.appendChild(card);
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

async function calificarHamburguesa(idHamburguesa, rating) {
    if (!usuarioActual) {
        alert("Debes iniciar sesión para calificar.");
        return;
    }

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

function actualizarRankingVisual(hamburguesa, usuarioActual) {
    const rankingContainer = document.querySelector(`.ranking-container[data-producto-id="${hamburguesa.id}"]`);
    if (!rankingContainer) return;

    const iconosRating = rankingContainer.querySelectorAll('.rating-icon');
    iconosRating.forEach(icon => {
        icon.src = "assets/fondos_recursos/flor-apagada.png"; // Resetea
    });

    if (usuarioActual && hamburguesa.ranking && hamburguesa.ranking.includes(usuarioActual)) {
        iconosRating.forEach((icon, index) => {
            icon.src = "assets/fondos_recursos/flor.png";
        });
    }
}