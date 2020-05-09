package com.pacificblack.informatebuenaventura.fragments.inicio;

import java.io.Serializable;

public class Inicio implements Serializable {

    String imagen_inicio, descripcion_corta;

    public Inicio() {
    }

    public Inicio(String imagen_inicio, String descripcion_corta, String video_del_dia) {
        this.imagen_inicio = imagen_inicio;
        this.descripcion_corta = descripcion_corta;
    }

    public String getImagen_inicio() {
        return imagen_inicio;
    }

    public void setImagen_inicio(String imagen_inicio) {
        this.imagen_inicio = imagen_inicio;
    }

    public String getDescripcion_corta() {
        return descripcion_corta;
    }



    public void setDescripcion_corta(String descripcion_corta) {
        this.descripcion_corta = descripcion_corta;
    }
}
