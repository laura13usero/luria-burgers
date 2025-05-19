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
        response.setContentType("application/json");

        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        // Ahora obtenemos el índice del cuerpo de la petición
        int index = Integer.parseInt(request.getParameter("index"));

        JsonObject jsonResponse = new JsonObject();

        if (session == null || usuario == null) {
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Usuario no logueado");
            response.getWriter().write(jsonResponse.toString());
            return;
        }

        CompraDAO compraDAO = new CompraDAO();
        List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");

        if (carrito != null && index >= 0 && index < carrito.size()) {
            int idProductoAEliminar = carrito.get(index).getId();
            carrito.remove(index);
            session.setAttribute("carrito", carrito);

            if (compraDAO.eliminarLineaCompra(usuario, idProductoAEliminar)) {
                jsonResponse.addProperty("status", "ok");
            } else {
                jsonResponse.addProperty("status", "error");
                jsonResponse.addProperty("message", "No se pudo eliminar el producto de la base de datos.");
            }
        } else {
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "Índice de producto inválido.");
        }

        response.getWriter().write(jsonResponse.toString());
    }
}