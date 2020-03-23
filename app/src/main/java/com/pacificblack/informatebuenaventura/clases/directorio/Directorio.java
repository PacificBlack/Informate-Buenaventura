package com.pacificblack.informatebuenaventura.clases.directorio;

import android.media.Image;

public class Directorio {

   private String titulo_row_directorio,descripcion_row_directorio,fechapublicacion_row_directorio,contactos_row_directorio;
    private int imagen1_directorio;
    private int vistas_row_directorio;

    public Directorio() {
    }

    public Directorio(String titulo_row_directorio, String descripcion_row_directorio, String fechapublicacion_row_directorio, String contactos_row_directorio, int imagen1_directorio, int vistas_row_directorio) {
        this.titulo_row_directorio = titulo_row_directorio;
        this.descripcion_row_directorio = descripcion_row_directorio;
        this.fechapublicacion_row_directorio = fechapublicacion_row_directorio;
        this.contactos_row_directorio = contactos_row_directorio;
        this.imagen1_directorio = imagen1_directorio;
        this.vistas_row_directorio = vistas_row_directorio;
    }

    public String getTitulo_row_directorio() {
        return titulo_row_directorio;
    }

    public void setTitulo_row_directorio(String titulo_row_directorio) {
        this.titulo_row_directorio = titulo_row_directorio;
    }

    public String getDescripcion_row_directorio() {
        return descripcion_row_directorio;
    }

    public void setDescripcion_row_directorio(String descripcion_row_directorio) {
        this.descripcion_row_directorio = descripcion_row_directorio;
    }

    public String getFechapublicacion_row_directorio() {
        return fechapublicacion_row_directorio;
    }

    public void setFechapublicacion_row_directorio(String fechapublicacion_row_directorio) {
        this.fechapublicacion_row_directorio = fechapublicacion_row_directorio;
    }

    public String getContactos_row_directorio() {
        return contactos_row_directorio;
    }

    public void setContactos_row_directorio(String contactos_row_directorio) {
        this.contactos_row_directorio = contactos_row_directorio;
    }

    public int getImagen1_directorio() {
        return imagen1_directorio;
    }

    public void setImagen1_directorio(int imagen1_directorio) {
        this.imagen1_directorio = imagen1_directorio;
    }

    public int getVistas_row_directorio() {
        return vistas_row_directorio;
    }

    public void setVistas_row_directorio(int vistas_row_directorio) {
        this.vistas_row_directorio = vistas_row_directorio;
    }
}
