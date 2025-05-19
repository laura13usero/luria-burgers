package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;
import util.CryptoUtils;
import util.MotorSQL;

public class UsuarioDAO {

    private Connection connection;

    public UsuarioDAO() {
        try {
            connection = MotorSQL.getConnection();
        } catch (SQLException e) {
            System.out.println("Error al obtener la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void agregarUsuario(Usuario usuario) throws SQLException {
        String queryUsuario = "INSERT INTO usuario (nombre, email, \"contraseña\", telefono, direccion, fecha_registro) VALUES (?, ?, ?, ?, ?, ?) RETURNING id_usuario";

        try (PreparedStatement stmt = connection.prepareStatement(queryUsuario)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getContrasena());
            stmt.setString(4, usuario.getTelefono());
            stmt.setString(5, usuario.getDireccion());
            stmt.setTimestamp(6, Timestamp.valueOf(usuario.getFechaRegistro()));
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getResultSet()) {
                if (generatedKeys.next()) {
                    int idUsuario = generatedKeys.getInt(1);

                    String queryRol = "INSERT INTO usuariorol (id_usuario, id_rol) VALUES (?, ?)";
                    try (PreparedStatement stmtRol = connection.prepareStatement(queryRol)) {
                        stmtRol.setInt(1, idUsuario);
                        stmtRol.setInt(2, 1);
                        stmtRol.executeUpdate();
                    }
                } else {
                    throw new SQLException("No se pudo obtener el ID del nuevo usuario.");
                }
            }
        }
    }

    public void agregarEmpleado(Usuario usuario) throws SQLException {
        String queryUsuario = "INSERT INTO usuario (nombre, email, \"contraseña\", telefono, direccion, fecha_registro) VALUES (?, ?, ?, ?, ?, ?) RETURNING id_usuario";

        try (PreparedStatement stmt = connection.prepareStatement(queryUsuario)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getContrasena());
            stmt.setString(4, usuario.getTelefono());
            stmt.setString(5, usuario.getDireccion());
            stmt.setTimestamp(6, Timestamp.valueOf(usuario.getFechaRegistro()));
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getResultSet()) {
                if (generatedKeys.next()) {
                    int idUsuario = generatedKeys.getInt(1);

                    String queryRol = "INSERT INTO usuariorol (id_usuario, id_rol) VALUES (?, ?)";
                    try (PreparedStatement stmtRol = connection.prepareStatement(queryRol)) {
                        stmtRol.setInt(1, idUsuario);
                        stmtRol.setInt(2, 3);
                        stmtRol.executeUpdate();
                    }
                } else {
                    throw new SQLException("No se pudo obtener el ID del nuevo usuario.");
                }
            }
        }
    }

    public List<Usuario> obtenerEmpleadosActivos() throws SQLException {
        return obtenerUsuariosPorRol(3);
    }

    public boolean actualizarTelefonoYDireccion(int idUsuario, String telefono, String direccion) throws SQLException {
        String sql = "UPDATE usuario SET telefono = ?, direccion = ? WHERE id_usuario = ?";
        try (Connection con = MotorSQL.getConnection(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, telefono);
            pst.setString(2, direccion);
            pst.setInt(3, idUsuario);
            return pst.executeUpdate() > 0;
        }
    }

    public List<Usuario> obtenerUsuariosPorRol(int rolId) throws SQLException {
        List<Usuario> empleados = new ArrayList<>();

        String query = "SELECT u.* FROM usuario u " +
                "JOIN usuariorol ur ON u.id_usuario = ur.id_usuario " +
                "WHERE ur.id_rol = ? AND u.activo = TRUE";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, rolId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setTelefono(rs.getString("telefono"));
                    u.setDireccion(rs.getString("direccion"));
                    u.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
                    empleados.add(u);
                }
            }
        }

        return empleados;
    }

    public boolean darDeBajaEmpleado(int idUsuario) throws SQLException {
        String query = "UPDATE usuario SET activo = FALSE WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        }
        return false;
    }

    public Usuario buscarPorEmailYContrasena(String email, String contrasena) {
        Usuario u = null;
        String sql = "SELECT u.id_usuario, u.nombre, u.email, u.\"contraseña\", u.telefono, u.direccion, u.fecha_registro, r.id_rol " +
                "FROM usuario u JOIN usuariorol r ON u.id_usuario = r.id_usuario WHERE u.email = ? AND u.\"contraseña\" = ?"; // Corregido

        // Encriptar la contraseña antes de hacer la consulta
        String contrasenaEncriptada = CryptoUtils.encriptarContrasena(contrasena);

        try (Connection con = MotorSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, contrasenaEncriptada);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setContrasena(rs.getString("contraseña"));
                    u.setTelefono(rs.getString("telefono"));
                    u.setDireccion(rs.getString("direccion"));
                    u.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());

                    String rol = getRolById(rs.getInt("id_rol"));
                    u.setRol(rol);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al buscar usuario", e);
        }

        return u;
    }

    public Usuario buscarPorEmail(String email) {
        Usuario u = null;
        String sql = "SELECT u.id_usuario, u.nombre, u.email, u.\"contraseña\", u.telefono, u.direccion, u.fecha_registro, r.id_rol " +
                "FROM usuario u JOIN usuariorol r ON u.id_usuario = r.id_usuario WHERE u.email = ?"; // Corregido

        try (Connection con = MotorSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setContrasena(rs.getString("contraseña"));
                    u.setTelefono(rs.getString("telefono"));
                    u.setDireccion(rs.getString("direccion"));
                    u.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());

                    String rol = getRolById(rs.getInt("id_rol"));
                    u.setRol(rol);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return u;
    }

    private String getRolById(int idRol) {
        String rol = "";
        String sql = "SELECT nombre FROM rol WHERE id_rol = ?";
        try (Connection con = MotorSQL.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idRol);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rol = rs.getString("nombre");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rol;
    }

    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT u.id_usuario, u.nombre, u.email, u.\"contraseña\", u.telefono, u.direccion, u.fecha_registro, r.id_rol " +
                "FROM usuario u JOIN usuariorol r ON u.id_usuario = r.id_usuario"; // Corregido
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("contraseña"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getTimestamp("fecha_registro").toLocalDateTime(),
                        getRolById(rs.getInt("id_rol"))
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}