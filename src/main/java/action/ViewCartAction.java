package action;

import dao.CompraDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Compra;
import model.Producto;
import model.Usuario;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal; // Importa BigDecimal

public class ViewCartAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);

        // Map para la respuesta JSON
        Map<String, Object> result = new HashMap<>();

        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/necesitalogin.html");
            return;
        } else {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            CompraDAO compraDAO = new CompraDAO();
            Compra compraActiva = compraDAO.getCompraActivaPorUsuario(usuario);

            List<Producto> carrito = null;
            BigDecimal total = BigDecimal.ZERO; // Usar BigDecimal para precisión

            if (compraActiva != null) {
                carrito = compraDAO.obtenerProductosDeCompra(compraActiva.getIdCompra());

                if (carrito != null) {
                    for (Producto p : carrito) {
                        if (p != null) {
                            total = total.add(BigDecimal.valueOf(p.getPrecio()).multiply(BigDecimal.valueOf(p.getCantidad()))); // Calcular total con cantidad
                        }
                    }
                }

                session.setAttribute("carrito", carrito);
                result.put("carrito", carrito);
                result.put("total", total.doubleValue()); // Convertir a double para la respuesta JSON

            } else {
                carrito = List.of();
                result.put("carrito", carrito);
                result.put("total", total.doubleValue());
                result.put("mensaje", "No hay compra activa");
            }

            String json = new Gson().toJson(result);
            response.setContentType("application/json");
            response.getWriter().write(json);
        }
    }
}