package com.pacificblack.informatebuenaventura.clases.donaciones;

import android.media.Image;

import java.io.Serializable;

public class Donaciones implements Serializable {

    private int id_donaciones;
    private String titulo_row_donaciones,descripcion_row_donaciones,fechapublicacion_row_donaciones;
    private String imagen1_donaciones,imagen2_donaciones;
    private int vistas_donaciones,meta_row_donaciones;
    private String descripcion1_donaciones,video_donaciones;

    public Donaciones() {
    }


    public Donaciones(int id_donaciones, String titulo_row_donaciones, String descripcion_row_donaciones, String fechapublicacion_row_donaciones, String imagen1_donaciones, String imagen2_donaciones, int vistas_donaciones, int meta_row_donaciones, String descripcion1_donaciones, String video_donaciones) {
        this.id_donaciones = id_donaciones;
        this.titulo_row_donaciones = titulo_row_donaciones;
        this.descripcion_row_donaciones = descripcion_row_donaciones;
        this.fechapublicacion_row_donaciones = fechapublicacion_row_donaciones;
        this.imagen1_donaciones = imagen1_donaciones;
        this.imagen2_donaciones = imagen2_donaciones;
        this.vistas_donaciones = vistas_donaciones;
        this.meta_row_donaciones = meta_row_donaciones;
        this.descripcion1_donaciones = descripcion1_donaciones;
        this.video_donaciones = video_donaciones;
    }

    public String getVideo_donaciones() {
        return video_donaciones;
    }

    public void setVideo_donaciones(String video_donaciones) {
        this.video_donaciones = video_donaciones;
    }

    public int getId_donaciones() {
        return id_donaciones;
    }

    public void setId_donaciones(int id_donaciones) {
        this.id_donaciones = id_donaciones;
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

    public String getImagen1_donaciones() {
        return imagen1_donaciones;
    }

    public void setImagen1_donaciones(String imagen1_donaciones) {
        this.imagen1_donaciones = imagen1_donaciones;
    }

    public String getImagen2_donaciones() {
        return imagen2_donaciones;
    }

    public void setImagen2_donaciones(String imagen2_donaciones) {
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
