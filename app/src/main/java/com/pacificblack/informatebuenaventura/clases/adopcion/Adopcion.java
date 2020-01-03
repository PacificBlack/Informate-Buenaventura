package com.pacificblack.informatebuenaventura.clases.adopcion;


import java.io.Serializable;

public class Adopcion implements Serializable {


    private String titulo_row_adopcion,descripcion_row_adopcion,fechapublicacion_row_desaparecidos;
    private int imagen1_adopcion,imagen2_adopcion,imagen3_adopcion,imagen4_adopcion;
    private int vistas_adopcion;
    private String descripcion1_adopcion,descripcion2_adopcion;

    public Adopcion() {
    }

    public Adopcion(String titulo_row_adopcion, String descripcion_row_adopcion, String fechapublicacion_row_desaparecidos, int imagen1_adopcion, int imagen2_adopcion, int imagen3_adopcion, int imagen4_adopcion, int vistas_adopcion, String descripcion1_adopcion, String descripcion2_adopcion) {
        this.titulo_row_adopcion = titulo_row_adopcion;
        this.descripcion_row_adopcion = descripcion_row_adopcion;
        this.fechapublicacion_row_desaparecidos = fechapublicacion_row_desaparecidos;
        this.imagen1_adopcion = imagen1_adopcion;
        this.imagen2_adopcion = imagen2_adopcion;
        this.imagen3_adopcion = imagen3_adopcion;
        this.imagen4_adopcion = imagen4_adopcion;
        this.vistas_adopcion = vistas_adopcion;
        this.descripcion1_adopcion = descripcion1_adopcion;
        this.descripcion2_adopcion = descripcion2_adopcion;
    }

    public String getTitulo_row_adopcion() {
        return titulo_row_adopcion;
    }

    public void setTitulo_row_adopcion(String titulo_row_adopcion) {
        this.titulo_row_adopcion = titulo_row_adopcion;
    }

    public String getDescripcion_row_adopcion() {
        return descripcion_row_adopcion;
    }

    public void setDescripcion_row_adopcion(String descripcion_row_adopcion) {
        this.descripcion_row_adopcion = descripcion_row_adopcion;
    }

    public String getFechapublicacion_row_desaparecidos() {
        return fechapublicacion_row_desaparecidos;
    }

    public void setFechapublicacion_row_desaparecidos(String fechapublicacion_row_desaparecidos) {
        this.fechapublicacion_row_desaparecidos = fechapublicacion_row_desaparecidos;
    }

    public int getImagen1_adopcion() {
        return imagen1_adopcion;
    }

    public void setImagen1_adopcion(int imagen1_adopcion) {
        this.imagen1_adopcion = imagen1_adopcion;
    }

    public int getImagen2_adopcion() {
        return imagen2_adopcion;
    }

    public void setImagen2_adopcion(int imagen2_adopcion) {
        this.imagen2_adopcion = imagen2_adopcion;
    }

    public int getImagen3_adopcion() {
        return imagen3_adopcion;
    }

    public void setImagen3_adopcion(int imagen3_adopcion) {
        this.imagen3_adopcion = imagen3_adopcion;
    }

    public int getImagen4_adopcion() {
        return imagen4_adopcion;
    }

    public void setImagen4_adopcion(int imagen4_adopcion) {
        this.imagen4_adopcion = imagen4_adopcion;
    }

    public int getVistas_adopcion() {
        return vistas_adopcion;
    }

    public void setVistas_adopcion(int vistas_adopcion) {
        this.vistas_adopcion = vistas_adopcion;
    }

    public String getDescripcion1_adopcion() {
        return descripcion1_adopcion;
    }

    public void setDescripcion1_adopcion(String descripcion1_adopcion) {
        this.descripcion1_adopcion = descripcion1_adopcion;
    }

    public String getDescripcion2_adopcion() {
        return descripcion2_adopcion;
    }

    public void setDescripcion2_adopcion(String descripcion2_adopcion) {
        this.descripcion2_adopcion = descripcion2_adopcion;
    }
}
