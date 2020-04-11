package com.pacificblack.informatebuenaventura.clases.encuestas;


import java.io.Serializable;

public class Encuestas implements Serializable {

    private  int id_encuestas;
    private String titulo_row_encuestas,descripcion_row_encuestas,fechapublicacion_row_encuestas;
    private String imagen1_encuestas;
    private int vistas_encuestas,voto1_encuestas,voto2_encuestas,voto3_encuestas,voto4_encuestas;
    private String opcion1,opcion2,opcion3,opcion4,descripcion1_encuestas;

    public Encuestas() {
    }


    public Encuestas(String titulo_row_encuestas, String descripcion_row_encuestas, String fechapublicacion_row_encuestas, String imagen1_encuestas, int vistas_encuestas, int voto1_encuestas, int voto2_encuestas, int voto3_encuestas, int voto4_encuestas, String opcion1, String opcion2, String opcion3, String opcion4, String descripcion1_encuestas, int id_encuestas) {
        this.id_encuestas = id_encuestas;
        this.titulo_row_encuestas = titulo_row_encuestas;
        this.descripcion_row_encuestas = descripcion_row_encuestas;
        this.fechapublicacion_row_encuestas = fechapublicacion_row_encuestas;
        this.imagen1_encuestas = imagen1_encuestas;
        this.vistas_encuestas = vistas_encuestas;
        this.voto1_encuestas = voto1_encuestas;
        this.voto2_encuestas = voto2_encuestas;
        this.voto3_encuestas = voto3_encuestas;
        this.voto4_encuestas = voto4_encuestas;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.opcion4 = opcion4;
        this.descripcion1_encuestas = descripcion1_encuestas;
    }


    public String getOpcion1() {
        return opcion1;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }

    public void setOpcion4(String opcion4) {
        this.opcion4 = opcion4;
    }

    public int getVoto1_encuestas() {
        return voto1_encuestas;
    }

    public void setVoto1_encuestas(int voto1_encuestas) {
        this.voto1_encuestas = voto1_encuestas;
    }

    public int getVoto2_encuestas() {
        return voto2_encuestas;
    }

    public void setVoto2_encuestas(int voto2_encuestas) {
        this.voto2_encuestas = voto2_encuestas;
    }

    public int getVoto3_encuestas() {
        return voto3_encuestas;
    }

    public void setVoto3_encuestas(int voto3_encuestas) {
        this.voto3_encuestas = voto3_encuestas;
    }

    public int getVoto4_encuestas() {
        return voto4_encuestas;
    }

    public void setVoto4_encuestas(int voto4_encuestas) {
        this.voto4_encuestas = voto4_encuestas;
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

    public int getVistas_encuestas() {
        return vistas_encuestas;
    }

    public void setVistas_encuestas(int vistas_encuestas) {
        this.vistas_encuestas = vistas_encuestas;
    }

    public String getDescripcion1_encuestas() {
        return descripcion1_encuestas;
    }

    public void setDescripcion1_encuestas(String descripcion1_encuestas) {
        this.descripcion1_encuestas = descripcion1_encuestas;
    }

    public int getId_encuestas() {
        return id_encuestas;
    }

    public void setId_encuestas(int id_encuestas) {
        this.id_encuestas = id_encuestas;
    }

    public String getImagen1_encuestas() {
        return imagen1_encuestas;
    }

    public void setImagen1_encuestas(String imagen1_encuestas) {
        this.imagen1_encuestas = imagen1_encuestas;
    }
}
