package com.pacificblack.informatebuenaventura.clases.desaparecidos;

public class Desaparecidos {

    String titulo_row_desaparecidos,descripcion_row_desaparecidos,fechapublicacion_row_desaparecidos,recompensa_row_desaparecidos;

    int vista_row_comprayventa;

    String fechadesaparecido_comprayventa,estado,ultimolugar_comprayventa,descripcion_desaparecidos;


    public Desaparecidos() {
    }

    public Desaparecidos(String titulo_row_desaparecidos, String descripcion_row_desaparecidos, String fechapublicacion_row_desaparecidos, String recompensa_row_desaparecidos, int vista_row_comprayventa, String fechadesaparecido_comprayventa, String estado, String ultimolugar_comprayventa, String descripcion_desaparecidos) {
        this.titulo_row_desaparecidos = titulo_row_desaparecidos;
        this.descripcion_row_desaparecidos = descripcion_row_desaparecidos;
        this.fechapublicacion_row_desaparecidos = fechapublicacion_row_desaparecidos;
        this.recompensa_row_desaparecidos = recompensa_row_desaparecidos;
        this.vista_row_comprayventa = vista_row_comprayventa;
        this.fechadesaparecido_comprayventa = fechadesaparecido_comprayventa;
        this.estado = estado;
        this.ultimolugar_comprayventa = ultimolugar_comprayventa;
        this.descripcion_desaparecidos = descripcion_desaparecidos;
    }

    public String getTitulo_row_desaparecidos() {
        return titulo_row_desaparecidos;
    }

    public void setTitulo_row_desaparecidos(String titulo_row_desaparecidos) {
        this.titulo_row_desaparecidos = titulo_row_desaparecidos;
    }

    public String getDescripcion_row_desaparecidos() {
        return descripcion_row_desaparecidos;
    }

    public void setDescripcion_row_desaparecidos(String descripcion_row_desaparecidos) {
        this.descripcion_row_desaparecidos = descripcion_row_desaparecidos;
    }

    public String getFechapublicacion_row_desaparecidos() {
        return fechapublicacion_row_desaparecidos;
    }

    public void setFechapublicacion_row_desaparecidos(String fechapublicacion_row_desaparecidos) {
        this.fechapublicacion_row_desaparecidos = fechapublicacion_row_desaparecidos;
    }

    public String getRecompensa_row_desaparecidos() {
        return recompensa_row_desaparecidos;
    }

    public void setRecompensa_row_desaparecidos(String recompensa_row_desaparecidos) {
        this.recompensa_row_desaparecidos = recompensa_row_desaparecidos;
    }

    public int getVista_row_comprayventa() {
        return vista_row_comprayventa;
    }

    public void setVista_row_comprayventa(int vista_row_comprayventa) {
        this.vista_row_comprayventa = vista_row_comprayventa;
    }

    public String getFechadesaparecido_comprayventa() {
        return fechadesaparecido_comprayventa;
    }

    public void setFechadesaparecido_comprayventa(String fechadesaparecido_comprayventa) {
        this.fechadesaparecido_comprayventa = fechadesaparecido_comprayventa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUltimolugar_comprayventa() {
        return ultimolugar_comprayventa;
    }

    public void setUltimolugar_comprayventa(String ultimolugar_comprayventa) {
        this.ultimolugar_comprayventa = ultimolugar_comprayventa;
    }

    public String getDescripcion_desaparecidos() {
        return descripcion_desaparecidos;
    }

    public void setDescripcion_desaparecidos(String descripcion_desaparecidos) {
        this.descripcion_desaparecidos = descripcion_desaparecidos;
    }
}
