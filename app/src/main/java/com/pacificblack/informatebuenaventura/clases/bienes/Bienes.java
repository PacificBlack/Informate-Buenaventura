package com.pacificblack.informatebuenaventura.clases.bienes;

import android.media.Image;

public class Bienes {

    String titulo_bienes,descripcion_row_bienes,descripcion_bienes,fechapublicacion_bienes,precio_bienes;

    int vista_bienes;

    Image imagen1_bienes,imagen2_bienes,imagen3_bienes;

    String ubicacion,contactos;


    public Bienes() {
    }

    public Bienes(String titulo_bienes, String descripcion_row_bienes, String descripcion_bienes, String fechapublicacion_bienes, String precio_bienes, int vista_bienes, Image imagen1_bienes, Image imagen2_bienes, Image imagen3_bienes, String ubicacion, String contactos) {
        this.titulo_bienes = titulo_bienes;
        this.descripcion_row_bienes = descripcion_row_bienes;
        this.descripcion_bienes = descripcion_bienes;
        this.fechapublicacion_bienes = fechapublicacion_bienes;
        this.precio_bienes = precio_bienes;
        this.vista_bienes = vista_bienes;
        this.imagen1_bienes = imagen1_bienes;
        this.imagen2_bienes = imagen2_bienes;
        this.imagen3_bienes = imagen3_bienes;
        this.ubicacion = ubicacion;
        this.contactos = contactos;
    }


    public String getTitulo_bienes() {
        return titulo_bienes;
    }

    public void setTitulo_bienes(String titulo_bienes) {
        this.titulo_bienes = titulo_bienes;
    }

    public String getDescripcion_row_bienes() {
        return descripcion_row_bienes;
    }

    public void setDescripcion_row_bienes(String descripcion_row_bienes) {
        this.descripcion_row_bienes = descripcion_row_bienes;
    }

    public String getDescripcion_bienes() {
        return descripcion_bienes;
    }

    public void setDescripcion_bienes(String descripcion_bienes) {
        this.descripcion_bienes = descripcion_bienes;
    }

    public String getFechapublicacion_bienes() {
        return fechapublicacion_bienes;
    }

    public void setFechapublicacion_bienes(String fechapublicacion_bienes) {
        this.fechapublicacion_bienes = fechapublicacion_bienes;
    }

    public String getPrecio_bienes() {
        return precio_bienes;
    }

    public void setPrecio_bienes(String precio_bienes) {
        this.precio_bienes = precio_bienes;
    }

    public int getVista_bienes() {
        return vista_bienes;
    }

    public void setVista_bienes(int vista_bienes) {
        this.vista_bienes = vista_bienes;
    }

    public Image getImagen1_bienes() {
        return imagen1_bienes;
    }

    public void setImagen1_bienes(Image imagen1_bienes) {
        this.imagen1_bienes = imagen1_bienes;
    }

    public Image getImagen2_bienes() {
        return imagen2_bienes;
    }

    public void setImagen2_bienes(Image imagen2_bienes) {
        this.imagen2_bienes = imagen2_bienes;
    }

    public Image getImagen3_bienes() {
        return imagen3_bienes;
    }

    public void setImagen3_bienes(Image imagen3_bienes) {
        this.imagen3_bienes = imagen3_bienes;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getContactos() {
        return contactos;
    }

    public void setContactos(String contactos) {
        this.contactos = contactos;
    }



}
