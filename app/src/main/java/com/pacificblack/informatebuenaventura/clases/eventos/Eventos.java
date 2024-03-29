package com.pacificblack.informatebuenaventura.clases.eventos;

import android.media.Image;

public class Eventos {

    private String titulo_row_eventos,descripcion_row_eventos,fechapublicacion_row_eventos,lugar_row_eventos;
    private String imagen1_eventos;
    private int vistas_eventos, id_eventos;

    public Eventos() {
    }

    public Eventos(String titulo_row_eventos, String descripcion_row_eventos, String fechapublicacion_row_eventos, String lugar_row_eventos, String imagen1_eventos, int vistas_eventos, int id_eventos) {
        this.titulo_row_eventos = titulo_row_eventos;
        this.descripcion_row_eventos = descripcion_row_eventos;
        this.fechapublicacion_row_eventos = fechapublicacion_row_eventos;
        this.lugar_row_eventos = lugar_row_eventos;
        this.imagen1_eventos = imagen1_eventos;
        this.vistas_eventos = vistas_eventos;
        this.id_eventos = id_eventos;
    }

    public int getId_eventos() {
        return id_eventos;
    }

    public void setId_eventos(int id_eventos) {
        this.id_eventos = id_eventos;
    }

    public String getTitulo_row_eventos() {
        return titulo_row_eventos;
    }

    public void setTitulo_row_eventos(String titulo_row_eventos) {
        this.titulo_row_eventos = titulo_row_eventos;
    }

    public String getDescripcion_row_eventos() {
        return descripcion_row_eventos;
    }

    public void setDescripcion_row_eventos(String descripcion_row_eventos) {
        this.descripcion_row_eventos = descripcion_row_eventos;
    }

    public String getFechapublicacion_row_eventos() {
        return fechapublicacion_row_eventos;
    }

    public void setFechapublicacion_row_eventos(String fechapublicacion_row_eventos) {
        this.fechapublicacion_row_eventos = fechapublicacion_row_eventos;
    }

    public String getLugar_row_eventos() {
        return lugar_row_eventos;
    }

    public void setLugar_row_eventos(String lugar_row_eventos) {
        this.lugar_row_eventos = lugar_row_eventos;
    }

    public String getImagen1_eventos() {
        return imagen1_eventos;
    }

    public void setImagen1_eventos(String imagen1_eventos) {
        this.imagen1_eventos = imagen1_eventos;
    }

    public int getVistas_eventos() {
        return vistas_eventos;
    }

    public void setVistas_eventos(int vistas_eventos) {
        this.vistas_eventos = vistas_eventos;
    }
}
