<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0">
  <meta name="description" content="Intranet de Luría's Burger">
  <meta name="theme-color" content="#D05A31">
  <title>Intranet Luría's Burger</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/intranet.css" rel="stylesheet">
  <link rel="icon" href="${pageContext.request.contextPath}/assets/fondos_recursos/flor.png" type="image/png">
</head>
<body>

<div class="container">
  <div class="welcome-message">
    <h2>Bienvenido a tu intranet, usuario. </h2>
    <p>Accede a todas las herramientas y recursos que necesitas como miembro del equipo Luría's Burger.</p>
  </div>

  <div class="tabs-container">
    <div class="zone-tabs" role="tablist">
      <div class="zone-tab active" role="tab" aria-selected="true" tabindex="0"
           onclick="openZone(event,'personal')" onkeydown="handleZoneKeyDown(event,'personal')">
        Zona Personal
      </div>
      <div class="zone-tab" role="tab" aria-selected="false" tabindex="0"
           onclick="openZone(event,'corporativa')" onkeydown="handleZoneKeyDown(event,'corporativa')">
        Zona Corporativa
      </div>
    </div>

    <!-- ZONA PERSONAL -->
    <div id="personal" class="zone-content active" role="tabpanel" aria-hidden="false">
      <p>La Zona Personal agrupa todas las herramientas y recursos para gestionar tu relación individual con la empresa.</p>
      <div class="module-grid">
        <div class="module-card" onclick="openModule('mi-nomina')">
          <div class="module-icon"><img src="${pageContext.request.contextPath}/assets/iconos_intranet/NOMINA.png" alt="Nómina"></div>
          <div class="module-title">Mi Nómina</div>
          <div class="module-desc">Consulta nóminas, histórico y certificados</div>
        </div>
        <div class="module-card" onclick="openModule('certificados')">
          <div class="module-icon"><img src="${pageContext.request.contextPath}/assets/iconos_intranet/CERTIFICADOS.png" alt="Certificados"></div>
          <div class="module-title">Certificados y Trámites</div>
          <div class="module-desc">Gestiona documentos y solicitudes</div>
        </div>
        <div class="module-card" onclick="openModule('vacaciones')">
          <div class="module-icon"><img src="${pageContext.request.contextPath}/assets/iconos_intranet/VACACIONES.png" alt="Vacaciones"></div>
          <div class="module-title">Vacaciones y Permisos</div>
          <div class="module-desc">Planifica tus días libres y permisos</div>
        </div>
        <div class="module-card" onclick="openModule('beneficios')">
          <div class="module-icon"><img src="${pageContext.request.contextPath}/assets/iconos_intranet/SALUD.png" alt="Beneficios"></div>
          <div class="module-title">Beneficios y Salud</div>
          <div class="module-desc">Programas de bienestar y ayudas</div>
        </div>
        <div class="module-card" onclick="openModule('denuncias')">
          <div class="module-icon"><img src="${pageContext.request.contextPath}/assets/iconos_intranet/DENUNCIAS.png" alt="Denuncias"></div>
          <div class="module-title">Canal de Denuncias</div>
          <div class="module-desc">Reporta conductas irregulares</div>
        </div>
      </div>
    </div>

    <!-- ZONA CORPORATIVA -->
    <div id="corporativa" class="zone-content" role="tabpanel" aria-hidden="true">
      <p>La Zona Corporativa ofrece información y servicios de carácter colectivo y organizativo.</p>
      <div class="module-grid">
        <div class="module-card" onclick="openModule('comite')">
          <div class="module-icon"><img src="${pageContext.request.contextPath}/assets/iconos_intranet/COMITE.png" alt="Comité"></div>
          <div class="module-title">Comité de Empresa</div>
          <div class="module-desc">Conoce a tus representantes</div>
        </div>
        <div class="module-card" onclick="openModule('sugerencias')">
          <div class="module-icon"><img src="${pageContext.request.contextPath}/assets/iconos_intranet/Captura de pantalla 2025-05-09 145940.png" alt="Sugerencias"></div>
          <div class="module-title">Buzón de Sugerencias & FAQ</div>
          <div class="module-desc">Envía ideas y consulta dudas</div>
        </div>
        <div class="module-card" onclick="openModule('calendario')">
          <div class="module-icon"><img src="${pageContext.request.contextPath}/assets/iconos_intranet/CALENDARIO.png" alt="Calendario"></div>
          <div class="module-title">Calendario Laboral</div>
          <div class="module-desc">Festivos, eventos y jornadas especiales</div>
        </div>
        <div class="module-card" onclick="openModule('normativa')">
          <div class="module-icon"><img src="${pageContext.request.contextPath}/assets/iconos_intranet/NORMATIVA.png" alt="Normativa"></div>
          <div class="module-title">Normativa & Convenio</div>
          <div class="module-desc">Leyes y convenios aplicables</div>
        </div>
        <div class="module-card" onclick="openModule('noticias')">
          <div class="module-icon"><img src="${pageContext.request.contextPath}/assets/iconos_intranet/NOTICIAS.png" alt="Noticias"></div>
          <div class="module-title">Noticias & Comunicados</div>
          <div class="module-desc">Mantente informado</div>
          <div class="notification-badge">3</div>
        </div>
        <div class="module-card" onclick="openModule('it')">
          <div class="module-icon"><img src="${pageContext.request.contextPath}/assets/iconos_intranet/CIBERSEGURIDAD.png" alt="IT"></div>
          <div class="module-title">IT & Ciberseguridad</div>
          <div class="module-desc">Soporte y buenas prácticas</div>
        </div>
        <div class="module-card" onclick="openModule('bienestar')">
          <div class="module-icon"><img src="${pageContext.request.contextPath}/assets/iconos_intranet/CULTURA.png" alt="Bienestar"></div>
          <div class="module-title">Bienestar & Cultura</div>
          <div class="module-desc">Actividades y reconocimientos</div>
        </div>
        <div class="module-card" onclick="openModule('sostenibilidad')">
          <div class="module-icon"><img src="${pageContext.request.contextPath}/assets/iconos_intranet/SOSTENIBILIDAD.png" alt="Sostenibilidad"></div>
          <div class="module-title">Sostenibilidad & RSC</div>
          <div class="module-desc">Compromiso medioambiental y social</div>
        </div>
        <div class="module-card" onclick="openModule('despidos')">
          <div class="module-icon"><img src="${pageContext.request.contextPath}/assets/iconos_intranet/DESPIDOS.png" alt="Despidos"></div>
          <div class="module-title">Despidos</div>
          <div class="module-desc">Tus derechos y obligaciones al finalizar la relación laboral</div>
        </div>
        <div class="module-card" onclick="openModule('prl')">
          <div class="module-icon"><img src="${pageContext.request.contextPath}/assets/iconos_intranet/icono_fuego.png" alt="Prevención de Riesgos"></div>
          <div class="module-title">Prevención de Riesgos Laborales</div>
          <div class="module-desc">Normas y recursos para un entorno seguro</div>
        </div>
      </div>
    </div>
  </div>
</div>

</body>
</html>
