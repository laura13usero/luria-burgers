let activeFilter = ''; // Variable para almacenar el filtro activo

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

                // Generar el HTML para el ranking de flores
                let rankingHTML = '<div class="ranking-container">';
                for (let i = 1; i <= 5; i++) {
                    rankingHTML += `<span class="hibisco ${i <= calcularPuntuacion(hamburguesa.ranking) ? 'active' : ''}" data-rating="${i}" onclick="calificarHamburguesa(${hamburguesa.id}, ${i})">🌺</span>`;
                }
                rankingHTML += '</div>';

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
        total += parseInt(ranking[i]);
    }
    let promedio = total / ranking.length;

    if (promedio <= 2) return 1;
    if (promedio <= 4) return 2;
    if (promedio <= 6) return 3;
    if (promedio <= 8) return 4;
    return 5;
}

function calificarHamburguesa(idHamburguesa, rating) {
    // Aquí implementarías la lógica para enviar la calificación al servidor
    console.log(`Hamburguesa ${idHamburguesa} calificada con ${rating}`);
    // Después de enviar la calificación y recibir una respuesta exitosa,
    // podrías recargar las hamburguesas para actualizar la visualización.
    // obtenerHamburguesas(activeFilter);
}

document.addEventListener('DOMContentLoaded', () => {
    obtenerHamburguesas(); // Carga todas las hamburguesas al inicio
});