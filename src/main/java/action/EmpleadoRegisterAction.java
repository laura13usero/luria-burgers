package action;

import dao.UsuarioDAO;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import model.Usuario;
import util.CryptoUtils;

import java.time.LocalDateTime;
import java.util.List;

public class EmpleadoRegisterAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        // Obtener los datos del formulario
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        // Encriptar la contraseña
        String contrasenaEncriptada = CryptoUtils.encriptarContrasena(contrasena);

        // Crear el usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContrasena(contrasenaEncriptada);
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setRol("empleado");  // Esto es solo informativo, no se usa en la inserción

        // Guardar en la base de datos
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.agregarEmpleado(usuario);

        // Obtener lista actualizada de empleados (rol 3)
        List<Usuario> empleados = usuarioDAO.obtenerUsuariosPorRol(3);

        // Preparar datos para mostrar en la JSP
        request.setAttribute("status", "ok");
        request.setAttribute("empleados", empleados);
        request.getRequestDispatcher("/jsp/registrarEmpleado.jsp").forward(request, response);
    }
}

//lalallalalalal
//jndjfjjejhfhsf
//lalalalalla

