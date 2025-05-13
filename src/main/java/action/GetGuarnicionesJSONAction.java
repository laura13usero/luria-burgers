package action;

import com.google.gson.Gson;
import dao.GuarnicionDAO;
import model.Producto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GetGuarnicionesJSONAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        GuarnicionDAO guarnicionDAO = new GuarnicionDAO();
        List<Producto> guarniciones = guarnicionDAO.obtenerGuarniciones();

        // Convertimos la lista de guarniciones en JSON
        String json = new Gson().toJson(guarniciones);

        // Configuramos la respuesta como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
