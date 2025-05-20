package action;

import com.google.gson.JsonObject;
import dao.CompraDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Producto;
import model.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.sql.SQLException;

public class ActualizarCantidadCarritoAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JsonObject json = new JsonObject();

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null) {
            json.addProperty("status", "error");
            json.addProperty("message", "Usuario no logueado.");
            out.print(json);
            return;
        }

        try {
            int index = Integer.parseInt(request.getParameter("index"));
            int cambio = Integer.parseInt(request.getParameter("cambio"));

            List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
            if (carrito == null || index < 0 || index >= carrito.size()) {
                json.addProperty("status", "error");
                json.addProperty("message", "Producto no encontrado en el carrito.");
                out.print(json);
                return;
            }

            Producto producto = carrito.get(index);
            CompraDAO compraDAO = new CompraDAO();

            // Pasar la HttpSession al método del DAO
            boolean actualizado = compraDAO.actualizarCantidadProductoEnCarrito(usuario, producto.getId(), cambio, session);

            if (actualizado) {
                json.addProperty("status", "ok");
                json.addProperty("message", "Cantidad actualizada correctamente.");
                json.addProperty("nuevaCantidad", producto.getCantidad());
                out.print(json);
            } else {
                json.addProperty("status", "error");
                json.addProperty("message", "No se pudo actualizar la cantidad.");
                out.print(json);
            }

        } catch (NumberFormatException | SQLException e) {
            json.addProperty("status", "error");
            json.addProperty("message", "Error al procesar la solicitud: " + e.getMessage());
            e.printStackTrace();
            out.print(json);
        }
    }
}

//lalalalala