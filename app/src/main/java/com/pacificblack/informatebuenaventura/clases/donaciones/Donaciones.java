package com.pacificblack.informatebuenaventura.clases.donaciones;

import android.media.Image;

import java.io.Serializable;

public class Donaciones implements Serializable {

    private String titulo_row_donaciones,descripcion_row_donaciones,fechapublicacion_row_donaciones;
    private int imagen1_donaciones,imagen2_donaciones;
    private int vistas_donaciones,meta_row_donaciones;
    private String descripcion1_donaciones;

    public Donaciones() {
    }

    public Donaciones(String titulo_row_donaciones, String descripcion_row_donaciones, String fechapublicacion_row_donaciones, int imagen1_donaciones, int imagen2_donaciones, int vistas_donaciones, int meta_row_donaciones, String descripcion1_donaciones) {
        this.titulo_row_donaciones = titulo_row_donaciones;
        this.descripcion_row_donaciones = descripcion_row_donaciones;
        this.fechapublicacion_row_donaciones = fechapublicacion_row_donaciones;
        this.imagen1_donaciones = imagen1_donaciones;
        this.imagen2_donaciones = imagen2_donaciones;
        this.vistas_donaciones = vistas_donaciones;
        this.meta_row_donaciones = meta_row_donaciones;
        this.descripcion1_donaciones = descripcion1_donaciones;
    }

    public String getTitulo_row_donaciones() {
        return titulo_row_donaciones;
    }

    public void setTitulo_row_donaciones(String titulo_row_donaciones) {
        this.titulo_row_donaciones = titulo_row_donaciones;
    }

    public String getDescripcion_row_donaciones() {
        return descripcion_row_donaciones;
    }

    public void setDescripcion_row_donaciones(String descripcion_row_donaciones) {
        this.descripcion_row_donaciones = descripcion_row_donaciones;
    }

    public String getFechapublicacion_row_donaciones() {
        return fechapublicacion_row_donaciones;
    }

    public void setFechapublicacion_row_donaciones(String fechapublicacion_row_donaciones) {
        this.fechapublicacion_row_donaciones = fechapublicacion_row_donaciones;
    }

    public int getImagen1_donaciones() {
        return imagen1_donaciones;
    }

    public void setImagen1_donaciones(int imagen1_donaciones) {
        this.imagen1_donaciones = imagen1_donaciones;
    }

    public int getImagen2_donaciones() {
        return imagen2_donaciones;
    }

    public void setImagen2_donaciones(int imagen2_donaciones) {
        this.imagen2_donaciones = imagen2_donaciones;
    }

    public int getVistas_donaciones() {
        return vistas_donaciones;
    }

    public void setVistas_donaciones(int vistas_donaciones) {
        this.vistas_donaciones = vistas_donaciones;
    }

    public int getMeta_row_donaciones() {
        return meta_row_donaciones;
    }

    public void setMeta_row_donaciones(int meta_row_donaciones) {
        this.meta_row_donaciones = meta_row_donaciones;
    }

    public String getDescripcion1_donaciones() {
        return descripcion1_donaciones;
    }

    public void setDescripcion1_donaciones(String descripcion1_donaciones) {
        this.descripcion1_donaciones = descripcion1_donaciones;
    }
}
