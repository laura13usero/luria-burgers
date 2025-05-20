package action;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.CompraDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Compra;
import model.Producto;
import model.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetResumenCompraAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        // Use GsonBuilder for pretty printing, useful for debugging
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        HttpSession session = request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        Map<String, Object> result = new HashMap<>();

        if (usuario == null) {
            result.put("status", "error");
            result.put("message", "Usuario no logueado.");
            out.print(gson.toJson(result));
            return;
        }

        CompraDAO compraDAO = new CompraDAO();
        try {
            // Get the active purchase for the user from the database
            Compra compraActiva = compraDAO.getCompraActivaPorUsuario(usuario);

            if (compraActiva != null) {
                // Get the products associated with this active purchase from the database
                List<Producto> productosEnCarrito = compraDAO.obtenerProductosDeCompra(compraActiva.getIdCompra());

                // The total is already calculated and stored in the 'compra' table
                // and should be reflected in the 'compraActiva' object's getTotal() method.
                BigDecimal totalCompra = compraActiva.getTotal();

                result.put("status", "ok");
                result.put("productos", productosEnCarrito); // Send products for displaying in the summary
                result.put("total", totalCompra.doubleValue()); // Send the calculated total
            } else {
                result.put("status", "ok");
                result.put("productos", List.of()); // No active purchase, so no products
                result.put("total", 0.0); // Total is zero
                result.put("message", "No hay compra activa para resumir.");
            }
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", "Error al obtener el resumen de la compra: " + e.getMessage());
            e.printStackTrace(); // Log the exception for debugging
        } finally {
            out.print(gson.toJson(result));
        }
    }
}