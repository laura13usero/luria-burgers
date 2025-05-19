package model;

import java.util.List;

public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private String categoria;
    private String filtros;
    private List<String> ranking;
    private String enlace_html;
    private String imagen_png;
    private double promedioRanking; // Nuevo campo
    private int cantidad; // Nuevo campo

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFiltros() {
        return filtros;
    }

    public void setFiltros(String filtros) {
        this.filtros = filtros;
    }

    public List<String> getRanking() {
        return ranking;
    }

    public void setRanking(List<String> ranking) {
        this.ranking = ranking;
    }

    public String getEnlace_html() {
        return enlace_html;
    }

    public void setEnlace_html(String enlace_html) {
        this.enlace_html = enlace_html;
    }

    public String getImagen_png() {
        return imagen_png;
    }

    public void setImagen_png(String imagen_png) {
        this.imagen_png = imagen_png;
    }

    public double getPromedioRanking() {
        return promedioRanking;
    }

    public void setPromedioRanking(double promedioRanking) {
        this.promedioRanking = promedioRanking;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}