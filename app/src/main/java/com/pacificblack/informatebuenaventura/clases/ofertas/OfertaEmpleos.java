package com.pacificblack.informatebuenaventura.clases.ofertas;

import android.media.Image;

public class OfertaEmpleos {

    private String titulo_row_ofertasempleos,descripcion_row_ofertasempleos,fechapublicacion_row_ofertasempleos,necesidad_row_ofertasempleos;
    private int imagen1_ofertasempleos;
    private int vistas_ofertasempleos;
    private String descripcion1_ofertasempleos,descripcion2_ofertasempleos;

    public OfertaEmpleos() {
    }

    public OfertaEmpleos(String titulo_row_ofertasempleos, String descripcion_row_ofertasempleos, String fechapublicacion_row_ofertasempleos, String necesidad_row_ofertasempleos, int imagen1_ofertasempleos, int vistas_ofertasempleos, String descripcion1_ofertasempleos, String descripcion2_ofertasempleos) {
        this.titulo_row_ofertasempleos = titulo_row_ofertasempleos;
        this.descripcion_row_ofertasempleos = descripcion_row_ofertasempleos;
        this.fechapublicacion_row_ofertasempleos = fechapublicacion_row_ofertasempleos;
        this.necesidad_row_ofertasempleos = necesidad_row_ofertasempleos;
        this.imagen1_ofertasempleos = imagen1_ofertasempleos;
        this.vistas_ofertasempleos = vistas_ofertasempleos;
        this.descripcion1_ofertasempleos = descripcion1_ofertasempleos;
        this.descripcion2_ofertasempleos = descripcion2_ofertasempleos;
    }

    public String getTitulo_row_ofertasempleos() {
        return titulo_row_ofertasempleos;
    }

    public void setTitulo_row_ofertasempleos(String titulo_row_ofertasempleos) {
        this.titulo_row_ofertasempleos = titulo_row_ofertasempleos;
    }

    public String getDescripcion_row_ofertasempleos() {
        return descripcion_row_ofertasempleos;
    }

    public void setDescripcion_row_ofertasempleos(String descripcion_row_ofertasempleos) {
        this.descripcion_row_ofertasempleos = descripcion_row_ofertasempleos;
    }

    public String getFechapublicacion_row_ofertasempleos() {
        return fechapublicacion_row_ofertasempleos;
    }

    public void setFechapublicacion_row_ofertasempleos(String fechapublicacion_row_ofertasempleos) {
        this.fechapublicacion_row_ofertasempleos = fechapublicacion_row_ofertasempleos;
    }

    public String getNecesidad_row_ofertasempleos() {
        return necesidad_row_ofertasempleos;
    }

    public void setNecesidad_row_ofertasempleos(String necesidad_row_ofertasempleos) {
        this.necesidad_row_ofertasempleos = necesidad_row_ofertasempleos;
    }

    public int getImagen1_ofertasempleos() {
        return imagen1_ofertasempleos;
    }

    public void setImagen1_ofertasempleos(int imagen1_ofertasempleos) {
        this.imagen1_ofertasempleos = imagen1_ofertasempleos;
    }

    public int getVistas_ofertasempleos() {
        return vistas_ofertasempleos;
    }

    public void setVistas_ofertasempleos(int vistas_ofertasempleos) {
        this.vistas_ofertasempleos = vistas_ofertasempleos;
    }

    public String getDescripcion1_ofertasempleos() {
        return descripcion1_ofertasempleos;
    }

    public void setDescripcion1_ofertasempleos(String descripcion1_ofertasempleos) {
        this.descripcion1_ofertasempleos = descripcion1_ofertasempleos;
    }

    public String getDescripcion2_ofertasempleos() {
        return descripcion2_ofertasempleos;
    }

    public void setDescripcion2_ofertasempleos(String descripcion2_ofertasempleos) {
        this.descripcion2_ofertasempleos = descripcion2_ofertasempleos;
    }
}
