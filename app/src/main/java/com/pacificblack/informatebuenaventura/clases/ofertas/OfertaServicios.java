package com.pacificblack.informatebuenaventura.clases.ofertas;

import android.media.Image;

public class OfertaServicios {

    private String titulo_row_ofertaservicios,descripcion_row_ofertaservicios,fechapublicacion_row_ofertaservicios,necesidad_row_ofertaservicios;
    private int imagen1_ofertaservicios;
    private int vistas_ofertaservicios;

    public OfertaServicios() {
    }

    public OfertaServicios(String titulo_row_ofertaservicios, String descripcion_row_ofertaservicios, String fechapublicacion_row_ofertaservicios, String necesidad_row_ofertaservicios, int imagen1_ofertaservicios, int vistas_ofertaservicios ) {
        this.titulo_row_ofertaservicios = titulo_row_ofertaservicios;
        this.descripcion_row_ofertaservicios = descripcion_row_ofertaservicios;
        this.fechapublicacion_row_ofertaservicios = fechapublicacion_row_ofertaservicios;
        this.necesidad_row_ofertaservicios = necesidad_row_ofertaservicios;
        this.imagen1_ofertaservicios = imagen1_ofertaservicios;
        this.vistas_ofertaservicios = vistas_ofertaservicios;
        }

    public String getTitulo_row_ofertaservicios() {
        return titulo_row_ofertaservicios;
    }

    public void setTitulo_row_ofertaservicios(String titulo_row_ofertaservicios) {
        this.titulo_row_ofertaservicios = titulo_row_ofertaservicios;
    }

    public String getDescripcion_row_ofertaservicios() {
        return descripcion_row_ofertaservicios;
    }

    public void setDescripcion_row_ofertaservicios(String descripcion_row_ofertaservicios) {
        this.descripcion_row_ofertaservicios = descripcion_row_ofertaservicios;
    }

    public String getFechapublicacion_row_ofertaservicios() {
        return fechapublicacion_row_ofertaservicios;
    }

    public void setFechapublicacion_row_ofertaservicios(String fechapublicacion_row_ofertaservicios) {
        this.fechapublicacion_row_ofertaservicios = fechapublicacion_row_ofertaservicios;
    }

    public String getNecesidad_row_ofertaservicios() {
        return necesidad_row_ofertaservicios;
    }

    public void setNecesidad_row_ofertaservicios(String necesidad_row_ofertaservicios) {
        this.necesidad_row_ofertaservicios = necesidad_row_ofertaservicios;
    }

    public int getImagen1_ofertaservicios() {
        return imagen1_ofertaservicios;
    }

    public void setImagen1_ofertaservicios(int imagen1_ofertaservicios) {
        this.imagen1_ofertaservicios = imagen1_ofertaservicios;
    }

    public int getVistas_ofertaservicios() {
        return vistas_ofertaservicios;
    }

    public void setVistas_ofertaservicios(int vistas_ofertaservicios) {
        this.vistas_ofertaservicios = vistas_ofertaservicios;
    }



}
