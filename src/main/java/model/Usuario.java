package model;

import java.time.LocalDateTime;



public class Usuario {
    private int idUsuario;
    private String nombre;
    private String email;
    private String contrasena;
    private String telefono;
    private LocalDateTime fechaRegistro;
    private String rol;  // Añadido el campo para el rol

    public Usuario() {}

    public Usuario(int idUsuario, String nombre, String email, String contrasena, String telefono, String direccion, LocalDateTime fechaRegistro, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
        this.rol = rol;  // Inicialización del rol
    }

    // Getter y Setter para rol
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    private boolean activo;

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", email=" + email + ", telefono=" + telefono
                 + ", fechaRegistro=" + fechaRegistro + ", rol=" + rol + "]";  // Mostramos también el rol
    }
}
