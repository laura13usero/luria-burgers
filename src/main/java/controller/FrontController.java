package controller;

import action.Action;
import actionfactory.ActionFactory;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import action.AddToCartAction;
import model.Usuario;
import dao.UsuarioDAO;

@WebServlet("/control")
public class FrontController extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        usuarioDAO = new UsuarioDAO(); // Inicializamos el DAO para interactuar con la base de datos
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String actionName = request.getParameter("action");

        if (actionName == null || actionName.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro 'action' requerido.");
            return;
        }

        // Aquí, agregamos la nueva acción para el carrito
        Action action = ActionFactory.getAction(actionName);

        if (action == null) {
            if (actionName.equals("addToCart")) {
                // Si la acción es 'addToCart', llamamos a la acción correspondiente
                AddToCartAction addToCartAction = new AddToCartAction();
                addToCartAction.execute(request, response);
                return;
            } else if (actionName.equals("UserLoginAction")) {
                // Lógica de login
                String email = request.getParameter("email");
                String contrasena = request.getParameter("contrasena");

                Usuario usuario = usuarioDAO.buscarPorEmailYContrasena(email, contrasena);

                if (usuario != null) {
                    // Guardamos el usuario en la sesión
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", usuario);

                    // Redirigir según el rol del usuario
                    String rol = usuario.getRol(); // Asumiendo que el rol está definido en el modelo Usuario

                    if ("admin".equals(rol)) {
                        response.sendRedirect("adminintranet.jsp");
                    } else if ("empleado".equals(rol)) {
                        response.sendRedirect("empleadointranet.jsp");
                    } else {
                        response.sendRedirect("indexintranet.jsp"); // Redirigir a un lugar por defecto si no tiene rol asignado
                    }
                } else {
                    // Si las credenciales son incorrectas, redirigir a login
                    response.sendRedirect("loginIntranet.jsp");
                }
                return;
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Acción no encontrada: " + actionName);
                return;
            }
        }

        // Ejecutamos la acción normalmente
        action.execute(request, response);
    }
}
