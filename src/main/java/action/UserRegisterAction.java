package action;

import dao.UsuarioDAO;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;
import model.Usuario;
import util.CryptoUtils;

import java.time.LocalDateTime;

public class UserRegisterAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        // Obtener los datos del formulario
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");  // La contraseña que nos pasa el usuario
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");

        // Encriptar la contraseña antes de guardarla
        String contrasenaEncriptada = CryptoUtils.encriptarContrasena(contrasena);

        // Crear un objeto Usuario con los datos y la contraseña encriptada
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setContrasena(contrasenaEncriptada);  // Usamos la contraseña encriptada
        usuario.setTelefono(telefono);
        usuario.setDireccion(direccion);
        usuario.setFechaRegistro(LocalDateTime.now());

        // Asignar el rol predeterminado (por ejemplo, 1 para "cliente")
        usuario.setRol("cliente"); // Si el rol es un String, si es un número, usa `setRol(1)`

        // Usar el UsuarioDAO para insertar el usuario
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.agregarUsuario(usuario);

        // Redirigir a otra página (por ejemplo, una página de éxito)
        response.sendRedirect(request.getContextPath() + "/jsp/registroExitoso.jsp");
    }
}
