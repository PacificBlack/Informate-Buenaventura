package com.pacificblack.informatebuenaventura.clases.bienes;


import android.media.Image;

public class Bienes {

    String titulo_row_bienes,descripcion_row_bienes,fechapublicacion_row_bienes;
    Image imagen1_bienes,imagen2_bienes,imagen3_bienes,imagen4_bienes;
    int precio_row_bienes,vistas_bienes;
    String descripcion1_bienes,descripcion2_bienes;

    public Bienes() {
    }

    public Bienes(String titulo_row_bienes, String descripcion_row_bienes, String fechapublicacion_row_bienes, Image imagen1_bienes, Image imagen2_bienes, Image imagen3_bienes, Image imagen4_bienes, int precio_row_bienes, int vistas_bienes, String descripcion1_bienes, String descripcion2_bienes) {
        this.titulo_row_bienes = titulo_row_bienes;
        this.descripcion_row_bienes = descripcion_row_bienes;
        this.fechapublicacion_row_bienes = fechapublicacion_row_bienes;
        this.imagen1_bienes = imagen1_bienes;
        this.imagen2_bienes = imagen2_bienes;
        this.imagen3_bienes = imagen3_bienes;
        this.imagen4_bienes = imagen4_bienes;
        this.precio_row_bienes = precio_row_bienes;
        this.vistas_bienes = vistas_bienes;
        this.descripcion1_bienes = descripcion1_bienes;
        this.descripcion2_bienes = descripcion2_bienes;
    }

    public String getTitulo_row_bienes() {
        return titulo_row_bienes;
    }

    public void setTitulo_row_bienes(String titulo_row_bienes) {
        this.titulo_row_bienes = titulo_row_bienes;
    }

    public String getDescripcion_row_bienes() {
        return descripcion_row_bienes;
    }

    public void setDescripcion_row_bienes(String descripcion_row_bienes) {
        this.descripcion_row_bienes = descripcion_row_bienes;
    }

    public String getFechapublicacion_row_bienes() {
        return fechapublicacion_row_bienes;
    }

    public void setFechapublicacion_row_bienes(String fechapublicacion_row_bienes) {
        this.fechapublicacion_row_bienes = fechapublicacion_row_bienes;
    }

    public Image getImagen1_bienes() {
        return imagen1_bienes;
    }

    public void setImagen1_bienes(Image imagen1_bienes) {
        this.imagen1_bienes = imagen1_bienes;
    }

    public Image getImagen2_bienes() {
        return imagen2_bienes;
    }

    public void setImagen2_bienes(Image imagen2_bienes) {
        this.imagen2_bienes = imagen2_bienes;
    }

    public Image getImagen3_bienes() {
        return imagen3_bienes;
    }

    public void setImagen3_bienes(Image imagen3_bienes) {
        this.imagen3_bienes = imagen3_bienes;
    }

    public Image getImagen4_bienes() {
        return imagen4_bienes;
    }

    public void setImagen4_bienes(Image imagen4_bienes) {
        this.imagen4_bienes = imagen4_bienes;
    }

    public int getPrecio_row_bienes() {
        return precio_row_bienes;
    }

    public void setPrecio_row_bienes(int precio_row_bienes) {
        this.precio_row_bienes = precio_row_bienes;
    }

    public int getVistas_bienes() {
        return vistas_bienes;
    }

    public void setVistas_bienes(int vistas_bienes) {
        this.vistas_bienes = vistas_bienes;
    }

    public String getDescripcion1_bienes() {
        return descripcion1_bienes;
    }

    public void setDescripcion1_bienes(String descripcion1_bienes) {
        this.descripcion1_bienes = descripcion1_bienes;
    }

    public String getDescripcion2_bienes() {
        return descripcion2_bienes;
    }

    public void setDescripcion2_bienes(String descripcion2_bienes) {
        this.descripcion2_bienes = descripcion2_bienes;
    }
}

