package action;

import com.google.gson.Gson;
import dao.GuarnicionDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Producto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GetGuarnicionesJSONAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        GuarnicionDAO guarnicionDAO = new GuarnicionDAO();
        List<Producto> guarniciones = guarnicionDAO.obtenerGuarniciones();

        String json = new Gson().toJson(guarniciones);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
