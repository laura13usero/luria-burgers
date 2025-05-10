package action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

public interface Action {
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException;
}

