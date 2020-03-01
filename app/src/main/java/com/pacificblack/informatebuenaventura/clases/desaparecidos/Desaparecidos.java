package com.pacificblack.informatebuenaventura.clases.desaparecidos;

import java.io.Serializable;

public class Desaparecidos implements Serializable {

    private int id_desaparecidos;
    private String titulo_row_desaparecidos,descripcion_row_desaparecidos,fechapublicacion_row_desaparecidos,recompensa_row_desaparecidos;
    private int vista_row_desaparecidos;
    private String imagen1_desaparecidos,imagen2_desaparecidos,imagen3̣̣_desaparecidos;
    private String descripcion1_desaparecidos,descripcion2_desaparecidos, fechadesaparecido_desaparecidos, estado_desaparecidos,ultimolugar_desaparecidos,que_desaparecidos;

    public Desaparecidos() {
    }


    public Desaparecidos(int id_desaparecidos, String titulo_row_desaparecidos, String descripcion_row_desaparecidos, String fechapublicacion_row_desaparecidos, String recompensa_row_desaparecidos, int vista_row_desaparecidos, String imagen1_desaparecidos, String imagen2_desaparecidos, String imagen3̣̣_desaparecidos, String descripcion1_desaparecidos, String descripcion2_desaparecidos, String fechadesaparecido_desaparecidos, String estado_desaparecidos, String ultimolugar_desaparecidos, String que_desaparecidos) {
        this.id_desaparecidos = id_desaparecidos;
        this.titulo_row_desaparecidos = titulo_row_desaparecidos;
        this.descripcion_row_desaparecidos = descripcion_row_desaparecidos;
        this.fechapublicacion_row_desaparecidos = fechapublicacion_row_desaparecidos;
        this.recompensa_row_desaparecidos = recompensa_row_desaparecidos;
        this.vista_row_desaparecidos = vista_row_desaparecidos;
        this.imagen1_desaparecidos = imagen1_desaparecidos;
        this.imagen2_desaparecidos = imagen2_desaparecidos;
        this.imagen3̣̣_desaparecidos = imagen3̣̣_desaparecidos;
        this.descripcion1_desaparecidos = descripcion1_desaparecidos;
        this.descripcion2_desaparecidos = descripcion2_desaparecidos;
        this.fechadesaparecido_desaparecidos = fechadesaparecido_desaparecidos;
        this.estado_desaparecidos = estado_desaparecidos;
        this.ultimolugar_desaparecidos = ultimolugar_desaparecidos;
        this.que_desaparecidos = que_desaparecidos;
    }

    public int getId_desaparecidos() {
        return id_desaparecidos;
    }

    public void setId_desaparecidos(int id_desaparecidos) {
        this.id_desaparecidos = id_desaparecidos;
    }

    public String getQue_desaparecidos() {
        return que_desaparecidos;
    }

    public void setQue_desaparecidos(String que_desaparecidos) {
        this.que_desaparecidos = que_desaparecidos;
    }

    public String getTitulo_row_desaparecidos() {
        return titulo_row_desaparecidos;
    }

    public void setTitulo_row_desaparecidos(String titulo_row_desaparecidos) {
        this.titulo_row_desaparecidos = titulo_row_desaparecidos;
    }

    public String getDescripcion_row_desaparecidos() {
        return descripcion_row_desaparecidos;
    }

    public void setDescripcion_row_desaparecidos(String descripcion_row_desaparecidos) {
        this.descripcion_row_desaparecidos = descripcion_row_desaparecidos;
    }

    public String getFechapublicacion_row_desaparecidos() {
        return fechapublicacion_row_desaparecidos;
    }

    public void setFechapublicacion_row_desaparecidos(String fechapublicacion_row_desaparecidos) {
        this.fechapublicacion_row_desaparecidos = fechapublicacion_row_desaparecidos;
    }

    public String getRecompensa_row_desaparecidos() {
        return recompensa_row_desaparecidos;
    }

    public void setRecompensa_row_desaparecidos(String recompensa_row_desaparecidos) {
        this.recompensa_row_desaparecidos = recompensa_row_desaparecidos;
    }

    public int getVista_row_desaparecidos() {
        return vista_row_desaparecidos;
    }

    public void setVista_row_desaparecidos(int vista_row_desaparecidos) {
        this.vista_row_desaparecidos = vista_row_desaparecidos;
    }

    public String getImagen1_desaparecidos() {
        return imagen1_desaparecidos;
    }

    public void setImagen1_desaparecidos(String imagen1_desaparecidos) {
        this.imagen1_desaparecidos = imagen1_desaparecidos;
    }

    public String getImagen2_desaparecidos() {
        return imagen2_desaparecidos;
    }

    public void setImagen2_desaparecidos(String imagen2_desaparecidos) {
        this.imagen2_desaparecidos = imagen2_desaparecidos;
    }

    public String getImagen3̣̣_desaparecidos() {
        return imagen3̣̣_desaparecidos;
    }

    public void setImagen3̣̣_desaparecidos(String imagen3̣̣_desaparecidos) {
        this.imagen3̣̣_desaparecidos = imagen3̣̣_desaparecidos;
    }

    public String getDescripcion1_desaparecidos() {
        return descripcion1_desaparecidos;
    }

    public void setDescripcion1_desaparecidos(String descripcion1_desaparecidos) {
        this.descripcion1_desaparecidos = descripcion1_desaparecidos;
    }

    public String getDescripcion2_desaparecidos() {
        return descripcion2_desaparecidos;
    }

    public void setDescripcion2_desaparecidos(String descripcion2_desaparecidos) {
        this.descripcion2_desaparecidos = descripcion2_desaparecidos;
    }

    public String getFechadesaparecido_desaparecidos() {
        return fechadesaparecido_desaparecidos;
    }

    public void setFechadesaparecido_desaparecidos(String fechadesaparecido_desaparecidos) {
        this.fechadesaparecido_desaparecidos = fechadesaparecido_desaparecidos;
    }

    public String getEstado_desaparecidos() {
        return estado_desaparecidos;
    }

    public void setEstado_desaparecidos(String estado_desaparecidos) {
        this.estado_desaparecidos = estado_desaparecidos;
    }

    public String getUltimolugar_desaparecidos() {
        return ultimolugar_desaparecidos;
    }

    public void setUltimolugar_desaparecidos(String ultimolugar_desaparecidos) {
        this.ultimolugar_desaparecidos = ultimolugar_desaparecidos;
    }
}
