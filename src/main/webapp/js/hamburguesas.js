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

                // Generar las flores de ranking
                let rankingHTML = `<div class="ranking-container" data-producto-id="${hamburguesa.id}">`;
                for (let i = 1; i <= 5; i++) {
                    let florSrc = "assets/fondos_recursos/flor-apagada.png";
                    if (hamburguesa.promedioRanking >= (i * 2) - 1 && hamburguesa.promedioRanking <= (i * 2)) {
                        florSrc = "assets/fondos_recursos/flor.png";
                    } else if (hamburguesa.promedioRanking > (i * 2)) {
                        florSrc = "assets/fondos_recursos/flor.png";
                    }
                    rankingHTML += `<img src="${florSrc}" alt="Calificación ${i}" class="rating-icon ${haVotado(usuarioActual, hamburguesa.id) ? 'votado' : ''}" data-rating="${i}"
                                    onclick="calificarHamburguesa(${hamburguesa.id}, ${i})">`;
                }
                rankingHTML += `</div>`;

                // Mostrar el promedio
                let promedioHTML = `<p class="promedio-ranking">Promedio: ${hamburguesa.promedioRanking.toFixed(1)}</p>`;


                card.innerHTML = `
                    <img src="${imagenSrc}" alt="${hamburguesa.nombre}">
                    <div class="burger-info">
                        <h3>${hamburguesa.nombre}</h3>
                        <p>${hamburguesa.descripcion}</p>
                        <p class="price">S/ ${hamburguesa.precio.toFixed(2)}</p>
                        ${rankingHTML}
                        ${promedioHTML}
                        <a href="${hamburguesa.enlace_html}" class="order-button">Pedir Ahora</a>
                    </div>
                `;
                container.appendChild(card);
            });
        } else {
            container.innerHTML = '<p>No se encontraron hamburguesas.</p>';
        }
    } catch (error) {
        console.error('Error al obtener las hamburguesas:', error);
        container.innerHTML = '<p>Error al cargar las hamburguesas.</p>';
    }
}

async function calificarHamburguesa(idHamburguesa, rating) {
    if (!usuarioActual) {
        alert("Debes iniciar sesión para calificar.");
        return;
    }

    if (haVotado(usuarioActual, idHamburguesa)) {
        alert("Ya has calificado esta hamburguesa.");
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
                usuario: `${usuarioActual}_${rating}`, // Incluir usuario y rating
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

function obtenerUsuarioActual() {
    try {
        const usuario = sessionStorage.getItem('usuarioLogueado');
        if (usuario) {
            return JSON.parse(usuario).email;
        }
        return null;
    } catch (error) {
        console.error("Error al obtener el usuario de sessionStorage:", error);
        return null;
    }
}

function haVotado(usuario, idProducto) {
    return votosUsuario[usuario] && votosUsuario[usuario][idProducto];
}

document.addEventListener('DOMContentLoaded', () => {
    usuarioActual = obtenerUsuarioActual();
    obtenerHamburguesas();
    console.log("Usuario actual (DOMContentLoaded):", usuarioActual);
});