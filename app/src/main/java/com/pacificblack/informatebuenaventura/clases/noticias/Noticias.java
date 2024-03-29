package com.pacificblack.informatebuenaventura.clases.noticias;

import android.media.Image;

import java.io.Serializable;
import java.net.URL;

public class Noticias implements Serializable {

    private int id_noticias;
    private String titulo_row_noticias,descripcion_row_noticias,fechapublicacion_row_noticias;
    private String imagen1_noticias,imagen2_noticias,imagen3_noticias,imagen4_noticias;
    private int vistas_noticias;
    private String descripcion1_noticias,descripcion2_noticias,descripcion3_noticias,video;

    public Noticias() {
    }

    public Noticias(String titulo_row_noticias, String descripcion_row_noticias, String fechapublicacion_row_noticias, String imagen1_noticias, String imagen2_noticias, String imagen3_noticias, String imagen4_noticias, int likes_noticias, int dislikes_noticias, int vistas_noticias, String descripcion1_noticias, String descripcion2_noticias, String descripcion3_noticias, String video, int id_noticias) {
        this.titulo_row_noticias = titulo_row_noticias;
        this.descripcion_row_noticias = descripcion_row_noticias;
        this.fechapublicacion_row_noticias = fechapublicacion_row_noticias;
        this.imagen1_noticias = imagen1_noticias;
        this.imagen2_noticias = imagen2_noticias;
        this.imagen3_noticias = imagen3_noticias;
        this.imagen4_noticias = imagen4_noticias;
        this.vistas_noticias = vistas_noticias;
        this.descripcion1_noticias = descripcion1_noticias;
        this.descripcion2_noticias = descripcion2_noticias;
        this.descripcion3_noticias = descripcion3_noticias;
        this.video = video;
        this.id_noticias = id_noticias;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTitulo_row_noticias() {
        return titulo_row_noticias;
    }

    public void setTitulo_row_noticias(String titulo_row_noticias) {
        this.titulo_row_noticias = titulo_row_noticias;
    }

    public String getDescripcion_row_noticias() {
        return descripcion_row_noticias;
    }

    public void setDescripcion_row_noticias(String descripcion_row_noticias) {
        this.descripcion_row_noticias = descripcion_row_noticias;
    }

    public String getFechapublicacion_row_noticias() {
        return fechapublicacion_row_noticias;
    }

    public void setFechapublicacion_row_noticias(String fechapublicacion_row_noticias) {
        this.fechapublicacion_row_noticias = fechapublicacion_row_noticias;
    }

    public int getId_noticias() {
        return id_noticias;
    }

    public void setId_noticias(int id_noticias) {
        this.id_noticias = id_noticias;
    }

    public String getImagen1_noticias() {
        return imagen1_noticias;
    }

    public void setImagen1_noticias(String imagen1_noticias) {
        this.imagen1_noticias = imagen1_noticias;
    }

    public String getImagen2_noticias() {
        return imagen2_noticias;
    }

    public void setImagen2_noticias(String imagen2_noticias) {
        this.imagen2_noticias = imagen2_noticias;
    }

    public String getImagen3_noticias() {
        return imagen3_noticias;
    }

    public void setImagen3_noticias(String imagen3_noticias) {
        this.imagen3_noticias = imagen3_noticias;
    }

    public String getImagen4_noticias() {
        return imagen4_noticias;
    }

    public void setImagen4_noticias(String imagen4_noticias) {
        this.imagen4_noticias = imagen4_noticias;
    }

    public int getVistas_noticias() {
        return vistas_noticias;
    }

    public void setVistas_noticias(int vistas_noticias) {
        this.vistas_noticias = vistas_noticias;
    }

    public String getDescripcion1_noticias() {
        return descripcion1_noticias;
    }

    public void setDescripcion1_noticias(String descripcion1_noticias) {
        this.descripcion1_noticias = descripcion1_noticias;
    }

    public String getDescripcion2_noticias() {
        return descripcion2_noticias;
    }

    public void setDescripcion2_noticias(String descripcion2_noticias) {
        this.descripcion2_noticias = descripcion2_noticias;
    }

    public String getDescripcion3_noticias() {
        return descripcion3_noticias;
    }

    public void setDescripcion3_noticias(String descripcion3_noticias) {
        this.descripcion3_noticias = descripcion3_noticias;
    }
}
