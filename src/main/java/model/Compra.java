package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Compra {
    private int idCompra;
    private int idUsuario;
    private Timestamp fechaHora;
    private String metodoPago;
    private BigDecimal total;

    public Compra(int idCompra, int idUsuario, Timestamp fechaHora, String metodoPago, BigDecimal total) {
        this.idCompra = idCompra;
        this.idUsuario = idUsuario;
        this.fechaHora = fechaHora;
        this.metodoPago = metodoPago;
        this.total = total;
    }

    // Getters y Setters
    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
