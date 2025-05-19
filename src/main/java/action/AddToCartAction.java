package action;

import com.google.gson.JsonObject;
import dao.ProductosDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.CompraDAO;
import model.Compra;
import model.Producto;
import model.Usuario;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AddToCartAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        JsonObject json = new JsonObject();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario != null) {
            try {
                int idProducto = Integer.parseInt(request.getParameter("idProducto"));
                int cantidad = 1;

                ProductosDAO productosDAO = new ProductosDAO();
                CompraDAO compraDAO = new CompraDAO();

                Producto producto = productosDAO.obtenerTodosLosProductos()
                        .stream()
                        .filter(p -> p.getId() == idProducto)
                        .findFirst()
                        .orElse(null);

                if (producto != null) {
                    BigDecimal subtotal = new BigDecimal(producto.getPrecio());
                    Compra compra = compraDAO.getCompraActivaPorUsuario(usuario);

                    if (compra == null) {
                        compra = compraDAO.crearCompra(usuario);
                        if (compra == null) {
                            json.addProperty("status", "error");
                            json.addProperty("message", "Error creating cart.");
                            out.print(json);
                            return;
                        }
                    }

                    boolean insertado = compraDAO.agregarLineaCompra(usuario, producto.getId(), producto.getCategoria(), cantidad, subtotal);
                    if (!insertado) {
                        json.addProperty("status", "error");
                        json.addProperty("message", "Error inserting product into cart.");
                        out.print(json);
                        return;
                    }

                    // Obtener el total actualizado de la base de datos y actualizar la compra
                    BigDecimal nuevoTotal = compraDAO.calcularTotalCompra(compra.getIdCompra());
                    compraDAO.actualizarTotalCompra(compra.getIdCompra(), nuevoTotal);

                    List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
                    if (carrito == null) {
                        carrito = new ArrayList<>();
                        session.setAttribute("carrito", carrito);
                    }
                    carrito.add(producto);

                    json.addProperty("status", "ok");
                    json.addProperty("redirect", request.getContextPath() + "/carrito.html");  // Nueva vista
                } else {
                    json.addProperty("status", "error");
                    json.addProperty("message", "Product not found.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                json.addProperty("status", "error");
                json.addProperty("message", "Unexpected error.");
            }

        } else {
            json.addProperty("status", "login_required");
            json.addProperty("redirect", request.getContextPath() + "/necesitalogin.html");
        }

        out.print(json);
    }
}