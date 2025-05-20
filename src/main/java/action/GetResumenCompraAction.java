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
            Compra compraActiva = compraDAO.getCompraActivaPorUsuario(usuario);

            if (compraActiva != null) {
                List<Producto> productosEnCarrito = compraDAO.obtenerProductosDeCompra(compraActiva.getIdCompra());
                BigDecimal totalCompra = compraActiva.getTotal();

                result.put("status", "ok");
                // ESTE ES EL CAMBIO CLAVE: Cambiado de "productos" a "carrito"
                result.put("carrito", productosEnCarrito); //
                result.put("total", totalCompra.doubleValue()); //
            } else {
                result.put("status", "ok");
                result.put("carrito", List.of()); //
                result.put("total", 0.0); //
                result.put("message", "No hay compra activa para resumir."); //
            }
        } catch (Exception e) {
            result.put("status", "error"); //
            result.put("message", "Error al obtener el resumen de la compra: " + e.getMessage()); //
            e.printStackTrace();
        } finally {
            out.print(gson.toJson(result)); //
        }
    }
}