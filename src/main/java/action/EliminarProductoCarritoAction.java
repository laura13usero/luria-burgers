package action;

import com.google.gson.JsonObject;
import dao.CompraDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Producto;
import model.Usuario;

import java.io.IOException;
import java.util.List;

public class EliminarProductoCarritoAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json"); // Importante: Establecer el tipo de contenido

        HttpSession session = request.getSession(false); // Obtener la sesión existente, no crear una nueva
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        int index = Integer.parseInt(request.getParameter("index")); // Obtener el índice del producto a eliminar

        JsonObject jsonResponse = new JsonObject(); // Para construir la respuesta JSON

        if (session == null || usuario == null) {
            // Usuario no logueado
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Usuario no logueado");
            response.getWriter().write(jsonResponse.toString());
            return; // Detener la ejecución
        }

        CompraDAO compraDAO = new CompraDAO();
        List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");

        if (carrito != null && index >= 0 && index < carrito.size()) {
            int idProductoAEliminar = carrito.get(index).getId(); // Obtener el ID del producto
            carrito.remove(index); // Eliminar del carrito en la sesión (¡ANTES de la base de datos!)
            session.setAttribute("carrito", carrito); // Actualizar el carrito en la sesión

            if (compraDAO.eliminarLineaCompra(usuario, idProductoAEliminar)) {
                // Eliminación exitosa de la base de datos
                jsonResponse.addProperty("status", "ok");
            } else {
                // Error al eliminar de la base de datos
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "No se pudo eliminar el producto de la base de datos.");
            }
        } else {
            // Índice inválido
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Índice de producto inválido.");
        }

        response.getWriter().write(jsonResponse.toString()); // Enviar la respuesta JSON
    }
}