package com.pacificblack.informatebuenaventura.clases.encuestas;

import android.media.Image;

public class Encuestas {

    private String titulo_row_encuestas,descripcion_row_encuestas,fechapublicacion_row_encuestas;
    private int imagen1_encuestas;
    private int vistas_encuestas,termina_encuestas;
    private String descripcion1_encuestas;

    public Encuestas() {
    }

    public Encuestas(String titulo_row_encuestas, String descripcion_row_encuestas, String fechapublicacion_row_encuestas, int imagen1_encuestas, int vistas_encuestas, int termina_encuestas, String descripcion1_encuestas) {
        this.titulo_row_encuestas = titulo_row_encuestas;
        this.descripcion_row_encuestas = descripcion_row_encuestas;
        this.fechapublicacion_row_encuestas = fechapublicacion_row_encuestas;
        this.imagen1_encuestas = imagen1_encuestas;
        this.vistas_encuestas = vistas_encuestas;
        this.termina_encuestas = termina_encuestas;
        this.descripcion1_encuestas = descripcion1_encuestas;
    }

    public String getTitulo_row_encuestas() {
        return titulo_row_encuestas;
    }

    public void setTitulo_row_encuestas(String titulo_row_encuestas) {
        this.titulo_row_encuestas = titulo_row_encuestas;
    }

    public String getDescripcion_row_encuestas() {
        return descripcion_row_encuestas;
    }

    public void setDescripcion_row_encuestas(String descripcion_row_encuestas) {
        this.descripcion_row_encuestas = descripcion_row_encuestas;
    }

    public String getFechapublicacion_row_encuestas() {
        return fechapublicacion_row_encuestas;
    }

    public void setFechapublicacion_row_encuestas(String fechapublicacion_row_encuestas) {
        this.fechapublicacion_row_encuestas = fechapublicacion_row_encuestas;
    }

    public int getImagen1_encuestas() {
        return imagen1_encuestas;
    }

    public void setImagen1_encuestas(int imagen1_encuestas) {
        this.imagen1_encuestas = imagen1_encuestas;
    }

    public int getVistas_encuestas() {
        return vistas_encuestas;
    }

    public void setVistas_encuestas(int vistas_encuestas) {
        this.vistas_encuestas = vistas_encuestas;
    }

    public int getTermina_encuestas() {
        return termina_encuestas;
    }

    public void setTermina_encuestas(int termina_encuestas) {
        this.termina_encuestas = termina_encuestas;
    }

    public String getDescripcion1_encuestas() {
        return descripcion1_encuestas;
    }

    public void setDescripcion1_encuestas(String descripcion1_encuestas) {
        this.descripcion1_encuestas = descripcion1_encuestas;
    }
}
