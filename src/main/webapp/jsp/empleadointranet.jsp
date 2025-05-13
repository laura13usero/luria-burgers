<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true" %>
<%
    // Verifica que el usuario tiene rol 'empleado'
    String rol = (String) session.getAttribute("rol");
    if (!"empleado".equals(rol)) {
        response.sendRedirect(request.getContextPath() + "/jsp/accesoDenegado.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="Intranet de Luría's Burger">
  <meta name="theme-color" content="#D05A31">
  <title>Intranet - Empleado</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/intranet.css">
  <link rel="icon" href="<%= request.getContextPath() %>/assets/fondos_recursos/flor.png" type="image/png">
  <style>
    .zone-content { display: none; }
    .zone-content.active { display: block; }
  </style>
</head>
<body>

  <div class="container">
    <% if ("ok".equals(request.getParameter("login"))) { %>
        <p style="color: green;">✅ Has iniciado sesión con éxito.</p>
    <% } %>

    <div class="welcome-message">
        <h1>Bienvenido, ${usuario.nombre}!</h1>
        <p>Accede a todas las herramientas y recursos que necesitas como miembro del equipo Luría's Burger.</p>
    </div>

    <div class="tabs-container">
      <div class="zone-tabs" role="tablist">
        <a href="#" class="zone-tab active" role="tab" aria-selected="true" tabindex="0" onclick="mostrarZona('personal')">
          Zona Personal
        </a>
        <a href="#" class="zone-tab" role="tab" aria-selected="false" tabindex="0" onclick="mostrarZona('corporativa')">
          Zona Corporativa
        </a>
      </div>
    </div>

    <!-- Zona Personal -->
    <div id="personal" class="zone-content active" role="tabpanel" aria-hidden="false">
        <p>La Zona Personal agrupa todas las herramientas y recursos para gestionar tu relación individual con la empresa.</p>
        <div class="module-grid">
            <div class="module-card" onclick="openModule('mi-nomina')">
                <div class="module-icon"><img src="<%= request.getContextPath() %>/assets/iconos_intranet/NOMINA.png" alt="Nómina"></div>
                <div class="module-title">Mi Nómina</div>
                <div class="module-desc">Consulta nóminas, histórico y certificados</div>
            </div>
            <div class="module-card" onclick="openModule('certificados')">
                <div class="module-icon"><img src="<%= request.getContextPath() %>/assets/iconos_intranet/CERTIFICADOS.png" alt="Certificados"></div>
                <div class="module-title">Certificados y Trámites</div>
                <div class="module-desc">Gestiona documentos y solicitudes</div>
            </div>
            <div class="module-card" onclick="openModule('vacaciones')">
                <div class="module-icon"><img src="<%= request.getContextPath() %>/assets/iconos_intranet/VACACIONES.png" alt="Vacaciones"></div>
                <div class="module-title">Vacaciones y Permisos</div>
                <div class="module-desc">Planifica tus días libres y permisos</div>
            </div>
            <div class="module-card" onclick="openModule('beneficios')">
                <div class="module-icon"><img src="<%= request.getContextPath() %>/assets/iconos_intranet/SALUD.png" alt="Beneficios"></div>
                <div class="module-title">Beneficios y Salud</div>
                <div class="module-desc">Programas de bienestar y ayudas</div>
            </div>
            <div class="module-card" onclick="openModule('denuncias')">
                <div class="module-icon"><img src="<%= request.getContextPath() %>/assets/iconos_intranet/DENUNCIAS.png" alt="Denuncias"></div>
                <div class="module-title">Canal de Denuncias</div>
                <div class="module-desc">Reporta conductas irregulares</div>
            </div>
        </div>
    </div>

    <!-- Zona Corporativa -->
    <div id="corporativa" class="zone-content" role="tabpanel" aria-hidden="true">
      <p>La Zona Corporativa ofrece información y servicios de carácter colectivo y organizativo.</p>
      <div class="module-grid">
        <div class="module-card" onclick="openModule('comite')">
          <div class="module-icon"><img src="<%= request.getContextPath() %>/assets/iconos_intranet/COMITE.png" alt="Comité"></div>
          <div class="module-title">Comité de Empresa</div>
          <div class="module-desc">Conoce a tus representantes</div>
        </div>
        <div class="module-card" onclick="openModule('sugerencias')">
          <div class="module-icon"><img src="<%= request.getContextPath() %>/assets/iconos_intranet/Captura de pantalla 2025-05-09 145940.png" alt="Sugerencias"></div>
          <div class="module-title">Buzón de Sugerencias & FAQ</div>
          <div class="module-desc">Envía ideas y consulta dudas</div>
        </div>
        <div class="module-card" onclick="openModule('calendario')">
          <div class="module-icon"><img src="<%= request.getContextPath() %>/assets/iconos_intranet/CALENDARIO.png" alt="Calendario"></div>
          <div class="module-title">Calendario Laboral</div>
          <div class="module-desc">Festivos, eventos y jornadas especiales</div>
        </div>
        <div class="module-card" onclick="openModule('normativa')">
          <div class="module-icon"><img src="<%= request.getContextPath() %>/assets/iconos_intranet/NORMATIVA.png" alt="Normativa"></div>
          <div class="module-title">Normativa & Convenio</div>
          <div class="module-desc">Leyes y convenios aplicables</div>
        </div>
        <div class="module-card" onclick="openModule('noticias')">
          <div class="module-icon"><img src="<%= request.getContextPath() %>/assets/iconos_intranet/NOTICIAS.png" alt="Noticias"></div>
          <div class="module-title">Noticias & Comunicados</div>
          <div class="module-desc">Mantente informado</div>
          <div class="notification-badge">3</div>
        </div>
        <div class="module-card" onclick="openModule('it')">
          <div class="module-icon"><img src="<%= request.getContextPath() %>/assets/iconos_intranet/CIBERSEGURIDAD.png" alt="IT"></div>
          <div class="module-title">IT & Ciberseguridad</div>
          <div class="module-desc">Soporte y buenas prácticas</div>
        </div>
        <div class="module-card" onclick="openModule('bienestar')">
          <div class="module-icon"><img src="<%= request.getContextPath() %>/assets/iconos_intranet/CULTURA.png" alt="Bienestar"></div>
          <div class="module-title">Bienestar & Cultura</div>
          <div class="module-desc">Actividades y reconocimientos</div>
        </div>
        <div class="module-card" onclick="openModule('sostenibilidad')">
          <div class="module-icon"><img src="<%= request.getContextPath() %>/assets/iconos_intranet/SOSTENIBILIDAD.png" alt="Sostenibilidad"></div>
          <div class="module-title">Sostenibilidad</div>
          <div class="module-desc">Iniciativas ecológicas</div>
        </div>
      </div>
    </div>

    <form action="<%= request.getContextPath() %>/control" method="post">
        <input type="hidden" name="action" value="logout">
        <button type="submit" class="logout-button">Cerrar sesión</button>
    </form>

  </div>

  <footer class="footer">
    <div class="container">
        <p>© 2025 Luría's Burger – Intranet</p>
        <p><a href="#">Política de privacidad</a> | <a href="#">Soporte técnico</a></p>
    </div>
  </footer>

  <script>
    function mostrarZona(zona) {
      const zonas = ['personal', 'corporativa'];
      zonas.forEach(z => {
        const contenido = document.getElementById(z);
        const tab = document.querySelector(`.zone-tab[onclick*="${z}"]`);
        if (z === zona) {
          contenido.classList.add('active');
          contenido.setAttribute('aria-hidden', 'false');
          tab.classList.add('active');
          tab.setAttribute('aria-selected', 'true');
        } else {
          contenido.classList.remove('active');
          contenido.setAttribute('aria-hidden', 'true');
          tab.classList.remove('active');
          tab.setAttribute('aria-selected', 'false');
        }
      });
    }

    // Mostrar la zona personal por defecto si no hay navegación previa
    document.addEventListener('DOMContentLoaded', function () {
      mostrarZona('personal');
    });
  </script>

</body>
</html>
