package com.pacificblack.informatebuenaventura.clases.clasificados;

import android.media.Image;

import java.io.Serializable;

public class Clasificados implements Serializable {

    private String titulo_row_clasificados,descripcion_row_clasificados,fechapublicacion_row_clasificados;
    private int imagen1_clasificados,imagen2_clasificados,imagen3_clasificados,imagen4_clasificados;
    private int vistas_bienes;
    private String descripcion1_clasificados,descripcion2_clasificados;

    public Clasificados() {
    }

    public Clasificados(String titulo_row_clasificados, String descripcion_row_clasificados, String fechapublicacion_row_clasificados, int imagen1_clasificados, int imagen2_clasificados, int imagen3_clasificados, int imagen4_clasificados, int vistas_bienes, String descripcion1_clasificados, String descripcion2_clasificados) {
        this.titulo_row_clasificados = titulo_row_clasificados;
        this.descripcion_row_clasificados = descripcion_row_clasificados;
        this.fechapublicacion_row_clasificados = fechapublicacion_row_clasificados;
        this.imagen1_clasificados = imagen1_clasificados;
        this.imagen2_clasificados = imagen2_clasificados;
        this.imagen3_clasificados = imagen3_clasificados;
        this.imagen4_clasificados = imagen4_clasificados;
        this.vistas_bienes = vistas_bienes;
        this.descripcion1_clasificados = descripcion1_clasificados;
        this.descripcion2_clasificados = descripcion2_clasificados;
    }

    public String getTitulo_row_clasificados() {
        return titulo_row_clasificados;
    }

    public void setTitulo_row_clasificados(String titulo_row_clasificados) {
        this.titulo_row_clasificados = titulo_row_clasificados;
    }

    public String getDescripcion_row_clasificados() {
        return descripcion_row_clasificados;
    }

    public void setDescripcion_row_clasificados(String descripcion_row_clasificados) {
        this.descripcion_row_clasificados = descripcion_row_clasificados;
    }

    public String getFechapublicacion_row_clasificados() {
        return fechapublicacion_row_clasificados;
    }

    public void setFechapublicacion_row_clasificados(String fechapublicacion_row_clasificados) {
        this.fechapublicacion_row_clasificados = fechapublicacion_row_clasificados;
    }

    public int getImagen1_clasificados() {
        return imagen1_clasificados;
    }

    public void setImagen1_clasificados(int imagen1_clasificados) {
        this.imagen1_clasificados = imagen1_clasificados;
    }

    public int getImagen2_clasificados() {
        return imagen2_clasificados;
    }

    public void setImagen2_clasificados(int imagen2_clasificados) {
        this.imagen2_clasificados = imagen2_clasificados;
    }

    public int getImagen3_clasificados() {
        return imagen3_clasificados;
    }

    public void setImagen3_clasificados(int imagen3_clasificados) {
        this.imagen3_clasificados = imagen3_clasificados;
    }

    public int getImagen4_clasificados() {
        return imagen4_clasificados;
    }

    public void setImagen4_clasificados(int imagen4_clasificados) {
        this.imagen4_clasificados = imagen4_clasificados;
    }

    public int getVistas_bienes() {
        return vistas_bienes;
    }

    public void setVistas_bienes(int vistas_bienes) {
        this.vistas_bienes = vistas_bienes;
    }

    public String getDescripcion1_clasificados() {
        return descripcion1_clasificados;
    }

    public void setDescripcion1_clasificados(String descripcion1_clasificados) {
        this.descripcion1_clasificados = descripcion1_clasificados;
    }

    public String getDescripcion2_clasificados() {
        return descripcion2_clasificados;
    }

    public void setDescripcion2_clasificados(String descripcion2_clasificados) {
        this.descripcion2_clasificados = descripcion2_clasificados;
    }
}
