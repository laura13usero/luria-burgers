package action;

import dao.GuarnicionDAO;
import model.Producto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.List;

public class GuarnicionesAction implements Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        GuarnicionDAO guarnicionDAO = new GuarnicionDAO();
        List<Producto> guarniciones = guarnicionDAO.obtenerGuarniciones();

        request.setAttribute("guarniciones", guarniciones);

        try {
            request.getRequestDispatcher("/jsp/guarniciones.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
