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

public class ViewCartAction implements Action {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");

        Map<String, Object> result = new HashMap<>();

        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            result.put("error", "Usuario no logueado");
        } else {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            CompraDAO compraDAO = new CompraDAO();
            Compra compraActiva = compraDAO.getCompraActivaPorUsuario(usuario);

            List<Producto> carrito = null;
            double total = 0.0;

            if (compraActiva != null) {
                carrito = compraDAO.obtenerProductosDeCompra(compraActiva.getIdCompra());

                if (carrito != null) {
                    for (Producto p : carrito) {
                        if (p != null) total += p.getPrecio();
                    }
                }

                session.setAttribute("carrito", carrito);
                result.put("carrito", carrito);
                result.put("total", total);
            } else {
                result.put("carrito", List.of());
                result.put("total", 0.0);
                result.put("mensaje", "No hay compra activa");
            }
        }

        String json = new Gson().toJson(result);
        response.getWriter().write(json);
    }
}
