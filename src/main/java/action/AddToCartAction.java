package action;

import dao.ProductosDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.CompraDAO;
import dao.HamburguesaDAO;
import model.Compra;
import model.Hamburguesa;
import model.Producto;
import model.Usuario;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AddToCartAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        System.out.println("🟡 ID producto recibido: " + request.getParameter("idProducto"));

        if (usuario != null) {
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            int cantidad = 1;  // Puedes adaptarlo si hay input del usuario

            ProductosDAO productosDAO = new ProductosDAO();
            CompraDAO compraDAO = new CompraDAO();

            Producto producto = null;

            for (Producto p : productosDAO.obtenerTodosLosProductos()) {
                if (p.getId() == idProducto) {
                    producto = p;
                    break;
                }
            }

            if (producto != null) {
                // Insertar en la base de datos (tabla lineacompra)
                BigDecimal subtotal = new BigDecimal(producto.getPrecio());

                // 1. Intentamos obtener la compra activa del usuario
                Compra compra = compraDAO.getCompraActivaPorUsuario(usuario);

                // 2. Si no existe una compra activa (pendiente), creamos una nueva
                if (compra == null) {
                    compra = compraDAO.crearCompra(usuario);  // Crear nueva compra pendiente
                    if (compra == null) {
                        System.out.println("🔴 No se pudo crear la compra.");
                    }
                }

                boolean insertado = compraDAO.agregarLineaCompra(usuario, producto.getId(), producto.getCategoria(), cantidad, subtotal);



                if (!insertado) {
                    // Aquí puedes añadir un log o mensaje de error si falla el insert
                    System.err.println("⚠️ No se pudo insertar la línea de compra.");
                }

                // 4. Actualizamos el total de la compra (en caso de que ya exista una compra pendiente)
                BigDecimal nuevoTotal = compra.getTotal().add(subtotal);
                boolean totalActualizado = compraDAO.actualizarTotalCompra(compra.getIdCompra(), nuevoTotal);

                // Actualizar carrito en sesión (opcional, solo visual)
                List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
                if (carrito == null) {
                    carrito = new ArrayList<>();
                    session.setAttribute("carrito", carrito);
                }

                carrito.add(producto);

                response.sendRedirect(request.getContextPath() + "/control?action=verCarrito");

            } else {
                response.sendRedirect(request.getContextPath() + "/jsp/hamburguesas.jsp?error=productoNoEncontrado");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/jsp/necesitalogin.jsp");
        }
    }
}
