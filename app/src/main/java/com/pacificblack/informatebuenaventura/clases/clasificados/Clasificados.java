package com.pacificblack.informatebuenaventura.clases.clasificados;

import android.media.Image;

public class Clasificados {

    String titulo_row_clasificados,descripcion_row_clasificados,fechapublicacion_row_clasificados,descripcion_clasificados,extras_clasificados;

    int vista_clasificados;

    Image imagen1_clasificados,imagen2_clasificados,imagen3_clasificados;


    public Clasificados() {
    }

    public Clasificados(String titulo_row_clasificados, String descripcion_row_clasificados, String fechapublicacion_row_clasificados, String descripcion_clasificados, String extras_clasificados, int vista_clasificados, Image imagen1_clasificados, Image imagen2_clasificados, Image imagen3_clasificados) {
        this.titulo_row_clasificados = titulo_row_clasificados;
        this.descripcion_row_clasificados = descripcion_row_clasificados;
        this.fechapublicacion_row_clasificados = fechapublicacion_row_clasificados;
        this.descripcion_clasificados = descripcion_clasificados;
        this.extras_clasificados = extras_clasificados;
        this.vista_clasificados = vista_clasificados;
        this.imagen1_clasificados = imagen1_clasificados;
        this.imagen2_clasificados = imagen2_clasificados;
        this.imagen3_clasificados = imagen3_clasificados;
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

    public String getDescripcion_clasificados() {
        return descripcion_clasificados;
    }

    public void setDescripcion_clasificados(String descripcion_clasificados) {
        this.descripcion_clasificados = descripcion_clasificados;
    }

    public String getExtras_clasificados() {
        return extras_clasificados;
    }

    public void setExtras_clasificados(String extras_clasificados) {
        this.extras_clasificados = extras_clasificados;
    }

    public int getVista_clasificados() {
        return vista_clasificados;
    }

    public void setVista_clasificados(int vista_clasificados) {
        this.vista_clasificados = vista_clasificados;
    }

    public Image getImagen1_clasificados() {
        return imagen1_clasificados;
    }

    public void setImagen1_clasificados(Image imagen1_clasificados) {
        this.imagen1_clasificados = imagen1_clasificados;
    }

    public Image getImagen2_clasificados() {
        return imagen2_clasificados;
    }

    public void setImagen2_clasificados(Image imagen2_clasificados) {
        this.imagen2_clasificados = imagen2_clasificados;
    }

    public Image getImagen3_clasificados() {
        return imagen3_clasificados;
    }

    public void setImagen3_clasificados(Image imagen3_clasificados) {
        this.imagen3_clasificados = imagen3_clasificados;
    }
}
