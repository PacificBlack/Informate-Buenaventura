package com.pacificblack.informatebuenaventura.clases.funebres;

import android.media.Image;

import java.io.Serializable;

public class Funebres implements Serializable {

    private String titulo_row_funebres,descripcion_row_funebres,fechapublicacion_row_funebres;
    private int imagen1_funebres,imagen2_funebres,imagen3_funebres;
    private int vistas_funebres;
    private String descripcion1_funebres,descripcion2_funebres;


    public Funebres() {
    }

    public Funebres(String titulo_row_funebres, String descripcion_row_funebres, String fechapublicacion_row_funebres, int imagen1_funebres, int imagen2_funebres, int imagen3_funebres, int vistas_funebres, String descripcion1_funebres, String descripcion2_funebres) {
        this.titulo_row_funebres = titulo_row_funebres;
        this.descripcion_row_funebres = descripcion_row_funebres;
        this.fechapublicacion_row_funebres = fechapublicacion_row_funebres;
        this.imagen1_funebres = imagen1_funebres;
        this.imagen2_funebres = imagen2_funebres;
        this.imagen3_funebres = imagen3_funebres;
        this.vistas_funebres = vistas_funebres;
        this.descripcion1_funebres = descripcion1_funebres;
        this.descripcion2_funebres = descripcion2_funebres;
    }

    public String getTitulo_row_funebres() {
        return titulo_row_funebres;
    }

    public void setTitulo_row_funebres(String titulo_row_funebres) {
        this.titulo_row_funebres = titulo_row_funebres;
    }

    public String getDescripcion_row_funebres() {
        return descripcion_row_funebres;
    }

    public void setDescripcion_row_funebres(String descripcion_row_funebres) {
        this.descripcion_row_funebres = descripcion_row_funebres;
    }

    public String getFechapublicacion_row_funebres() {
        return fechapublicacion_row_funebres;
    }

    public void setFechapublicacion_row_funebres(String fechapublicacion_row_funebres) {
        this.fechapublicacion_row_funebres = fechapublicacion_row_funebres;
    }

    public int getImagen1_funebres() {
        return imagen1_funebres;
    }

    public void setImagen1_funebres(int imagen1_funebres) {
        this.imagen1_funebres = imagen1_funebres;
    }

    public int getImagen2_funebres() {
        return imagen2_funebres;
    }

    public void setImagen2_funebres(int imagen2_funebres) {
        this.imagen2_funebres = imagen2_funebres;
    }

    public int getImagen3_funebres() {
        return imagen3_funebres;
    }

    public void setImagen3_funebres(int imagen3_funebres) {
        this.imagen3_funebres = imagen3_funebres;
    }

    public int getVistas_funebres() {
        return vistas_funebres;
    }

    public void setVistas_funebres(int vistas_funebres) {
        this.vistas_funebres = vistas_funebres;
    }

    public String getDescripcion1_funebres() {
        return descripcion1_funebres;
    }

    public void setDescripcion1_funebres(String descripcion1_funebres) {
        this.descripcion1_funebres = descripcion1_funebres;
    }

    public String getDescripcion2_funebres() {
        return descripcion2_funebres;
    }

    public void setDescripcion2_funebres(String descripcion2_funebres) {
        this.descripcion2_funebres = descripcion2_funebres;
    }
}
