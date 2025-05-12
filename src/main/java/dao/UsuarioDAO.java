package dao;

import model.Usuario;
import util.MotorSQL;
import util.CryptoUtils;  // Asegúrate de que esta clase esté definida para encriptar las contraseñas

import java.sql.*;
import java.util.*;

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
        String queryUsuario = "INSERT INTO Usuario (nombre, email, contraseña, telefono, direccion, fecha_registro) VALUES (?, ?, ?, ?, ?, ?)";

        // 1. Insertar el usuario y obtener el ID generado
        try (PreparedStatement stmt = connection.prepareStatement(queryUsuario, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getContrasena()); // Contraseña ya encriptada
            stmt.setString(4, usuario.getTelefono());
            stmt.setString(5, usuario.getDireccion());
            stmt.setTimestamp(6, Timestamp.valueOf(usuario.getFechaRegistro()));
            stmt.executeUpdate();

            // 2. Obtener el ID autogenerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idUsuario = generatedKeys.getInt(1);

                    // 3. Insertar en la tabla usuariorol (Asumimos que el rol predeterminado es 1 para "cliente")
                    String queryRol = "INSERT INTO usuariorol (id_usuario, id_rol) VALUES (?, ?)";
                    try (PreparedStatement stmtRol = connection.prepareStatement(queryRol)) {
                        stmtRol.setInt(1, idUsuario);
                        stmtRol.setInt(2, 1);  // Asignamos rol 1 por defecto (puedes cambiarlo según corresponda)
                        stmtRol.executeUpdate();
                    }
                } else {
                    throw new SQLException("No se pudo obtener el ID del nuevo usuario.");
                }
            }
        }
    }

    public void agregarEmpleado(Usuario usuario) throws SQLException {
        String queryUsuario = "INSERT INTO Usuario (nombre, email, contraseña, telefono, direccion, fecha_registro) VALUES (?, ?, ?, ?, ?, ?)";

        // 1. Insertar el usuario y obtener el ID generado
        try (PreparedStatement stmt = connection.prepareStatement(queryUsuario, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getContrasena()); // Contraseña ya encriptada
            stmt.setString(4, usuario.getTelefono());
            stmt.setString(5, usuario.getDireccion());
            stmt.setTimestamp(6, Timestamp.valueOf(usuario.getFechaRegistro()));
            stmt.executeUpdate();

            // 2. Obtener el ID autogenerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idUsuario = generatedKeys.getInt(1);

                    // 3. Insertar en la tabla usuariorol (Asumimos que el rol predeterminado es 1 para "cliente")
                    String queryRol = "INSERT INTO usuariorol (id_usuario, id_rol) VALUES (?, ?)";
                    try (PreparedStatement stmtRol = connection.prepareStatement(queryRol)) {
                        stmtRol.setInt(1, idUsuario);
                        stmtRol.setInt(2, 3);  // Asignamos rol 3 por defecto
                        stmtRol.executeUpdate();
                    }
                } else {
                    throw new SQLException("No se pudo obtener el ID del nuevo usuario.");
                }
            }
        }
    }


    public List<Usuario> obtenerEmpleadosActivos() throws SQLException {
        // Aquí asumimos que el rol 3 es el de EMPLEADO, como indicaste antes.
        return obtenerUsuariosPorRol(3);
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

    public void darDeBajaEmpleado(int idUsuario) throws SQLException {
        String query = "UPDATE usuario SET activo = FALSE WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        }
    }



    public Usuario buscarPorEmailYContrasena(String email, String contrasena) {
        Usuario u = null;
        String sql = "SELECT u.id_usuario, u.nombre, u.email, u.contraseña, u.telefono, u.direccion, u.fecha_registro, r.id_rol " +
                "FROM usuario u JOIN usuariorol r ON u.id_usuario = r.id_usuario WHERE u.email = ? AND u.contraseña = ?";

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

                    // Asignamos el rol al usuario
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
        String sql = "SELECT u.id_usuario, u.nombre, u.email, u.contraseña, u.telefono, u.direccion, u.fecha_registro, r.id_rol " +
                "FROM usuario u JOIN usuariorol r ON u.id_usuario = r.id_usuario WHERE u.email = ?";

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

                    // Asignamos el rol al usuario
                    String rol = getRolById(rs.getInt("id_rol"));
                    u.setRol(rol);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return u;
    }

    // Método para obtener el rol por ID de rol
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


    // Método para obtener todos los usuarios
    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT u.id_usuario, u.nombre, u.email, u.contraseña, u.telefono, u.direccion, u.fecha_registro, r.id_rol " +
                "FROM usuario u JOIN usuariorol r ON u.id_usuario = r.id_usuario";
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
                        getRolById(rs.getInt("id_rol"))  // Agregar el rol
                );
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}
