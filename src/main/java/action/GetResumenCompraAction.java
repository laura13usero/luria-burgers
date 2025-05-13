package action;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.*;
import model.Producto;
import com.google.gson.Gson;

public class GetResumenCompraAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        double total = carrito.stream().mapToDouble(Producto::getPrecio).sum();

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("carrito", carrito);
        resultado.put("total", total);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(resultado));
    }
}
