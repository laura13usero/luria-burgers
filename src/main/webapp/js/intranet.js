function openZone(evt, zoneName) {
  // Ocultar todos los contenidos de zona
  let zoneContents = document.querySelectorAll('.zone-content');
  zoneContents.forEach(zoneContent => {
    zoneContent.style.display = 'none';
    zoneContent.setAttribute('aria-hidden', 'true');
  });

  // Quitar clase 'active' de todas las pestañas y resetear aria-selected
  let zoneTabs = document.querySelectorAll('.zone-tab');
  zoneTabs.forEach(tab => {
    tab.classList.remove('active');
    tab.setAttribute('aria-selected', 'false');
  });

  // Mostrar el contenido de la zona seleccionada y marcar la pestaña como activa
  const currentZoneContent = document.getElementById(zoneName);
  if (currentZoneContent) {
    currentZoneContent.style.display = 'block';
    currentZoneContent.setAttribute('aria-hidden', 'false');
  }
  
  const currentTab = evt.currentTarget;
  if (currentTab) {
    currentTab.classList.add('active');
    currentTab.setAttribute('aria-selected', 'true');
  }
}

function handleZoneKeyDown(event, zoneName) {
  if (event.key === 'Enter' || event.key === ' ') {
    event.preventDefault(); // Evitar scroll en caso de 'espacio'
    openZone(event, zoneName);
  }
}

function openModule(moduleName) {
  const popup = document.getElementById(moduleName + '-popup');
  if (popup) {
    popup.style.display = 'flex';
    document.body.style.overflow = 'hidden'; // Evitar scroll del fondo
    // Opcional: Enfocar el popup o su primer elemento enfocable
    const closeButton = popup.querySelector('.popup-close');
    if(closeButton) closeButton.focus(); 
  }
}

function closeModule(moduleName) {
  const popup = document.getElementById(moduleName + '-popup');
  if (popup) {
    popup.style.display = 'none';
    document.body.style.overflow = ''; // Restaurar scroll del fondo
    // Opcional: Devolver el foco al botón que abrió el modal si es posible/necesario
  }
}

// Event listener para cerrar popups haciendo clic fuera de su contenido
document.addEventListener('click', function(event) {
  if (event.target.classList.contains('module-popup')) {
    // El ID del popup es (moduleName + '-popup'), necesitamos extraer moduleName
    const moduleName = event.target.id.replace('-popup', '');
    closeModule(moduleName);
  }
});

// Event listener para cerrar popups con la tecla Escape
document.addEventListener('keydown', function(event) {
  if (event.key === 'Escape') {
    const openPopups = document.querySelectorAll('.module-popup[style*="display: flex"]');
    openPopups.forEach(popup => {
      const moduleName = popup.id.replace('-popup', '');
      closeModule(moduleName);
    });
  }
});

// Asegurarse de que la zona personal esté visible al cargar la página
document.addEventListener('DOMContentLoaded', () => {
  const personalZone = document.getElementById('personal');
  if (personalZone) {
    personalZone.style.display = 'block'; // Ya está 'active' por defecto en el HTML
    // No es necesario llamar a openZone aquí si la pestaña 'personal' ya tiene 'active'
  }
  // Ocultar 'corporativa' explícitamente si no se hace por defecto con CSS
  const corporativaZone = document.getElementById('corporativa');
  if (corporativaZone && !corporativaZone.classList.contains('active')) {
      corporativaZone.style.display = 'none';
  }
});