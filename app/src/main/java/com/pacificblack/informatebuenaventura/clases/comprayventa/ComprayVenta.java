package com.pacificblack.informatebuenaventura.clases.comprayventa;

import android.media.Image;

import java.io.Serializable;

public class ComprayVenta implements Serializable {


    private int id_comprayventa;
    private String titulo_row_comprayventa,descripcion_row_comprayventa,descripcion_comprayventa,fechapublicacion_row_comprayventa,precio_row_comprayventa;
    private String contacto_comprayventa,ubicacion_comprayventa,descripcionextra_comprayventa;
    private String imagen1_comprayventa,imagen2_comprayventa,imagen3̣̣_comprayventa;
    private int cantidad_comprayventa,vista_comprayventa;

    public ComprayVenta() {
    }


    public ComprayVenta(int id_comprayventa, String titulo_row_comprayventa, String descripcion_row_comprayventa, String descripcion_comprayventa, String fechapublicacion_row_comprayventa, String precio_row_comprayventa, String contacto_comprayventa, String ubicacion_comprayventa, String descripcionextra_comprayventa, String imagen1_comprayventa, String imagen2_comprayventa, String imagen3̣̣_comprayventa, int cantidad_comprayventa, int vista_comprayventa) {
        this.id_comprayventa = id_comprayventa;
        this.titulo_row_comprayventa = titulo_row_comprayventa;
        this.descripcion_row_comprayventa = descripcion_row_comprayventa;
        this.descripcion_comprayventa = descripcion_comprayventa;
        this.fechapublicacion_row_comprayventa = fechapublicacion_row_comprayventa;
        this.precio_row_comprayventa = precio_row_comprayventa;
        this.contacto_comprayventa = contacto_comprayventa;
        this.ubicacion_comprayventa = ubicacion_comprayventa;
        this.descripcionextra_comprayventa = descripcionextra_comprayventa;
        this.imagen1_comprayventa = imagen1_comprayventa;
        this.imagen2_comprayventa = imagen2_comprayventa;
        this.imagen3̣̣_comprayventa = imagen3̣̣_comprayventa;
        this.cantidad_comprayventa = cantidad_comprayventa;
        this.vista_comprayventa = vista_comprayventa;
    }

    public int getId_comprayventa() {
        return id_comprayventa;
    }

    public void setId_comprayventa(int id_comprayventa) {
        this.id_comprayventa = id_comprayventa;
    }

    public String getImagen1_comprayventa() {
        return imagen1_comprayventa;
    }

    public void setImagen1_comprayventa(String imagen1_comprayventa) {
        this.imagen1_comprayventa = imagen1_comprayventa;
    }

    public String getImagen2_comprayventa() {
        return imagen2_comprayventa;
    }

    public void setImagen2_comprayventa(String imagen2_comprayventa) {
        this.imagen2_comprayventa = imagen2_comprayventa;
    }

    public String getImagen3̣̣_comprayventa() {
        return imagen3̣̣_comprayventa;
    }

    public void setImagen3̣̣_comprayventa(String imagen3̣̣_comprayventa) {
        this.imagen3̣̣_comprayventa = imagen3̣̣_comprayventa;
    }

    public String getTitulo_row_comprayventa() {
        return titulo_row_comprayventa;
    }

    public void setTitulo_row_comprayventa(String titulo_row_comprayventa) {
        this.titulo_row_comprayventa = titulo_row_comprayventa;
    }

    public String getDescripcion_row_comprayventa() {
        return descripcion_row_comprayventa;
    }

    public void setDescripcion_row_comprayventa(String descripcion_row_comprayventa) {
        this.descripcion_row_comprayventa = descripcion_row_comprayventa;
    }

    public String getDescripcion_comprayventa() {
        return descripcion_comprayventa;
    }

    public void setDescripcion_comprayventa(String descripcion_comprayventa) {
        this.descripcion_comprayventa = descripcion_comprayventa;
    }

    public String getFechapublicacion_row_comprayventa() {
        return fechapublicacion_row_comprayventa;
    }

    public void setFechapublicacion_row_comprayventa(String fechapublicacion_row_comprayventa) {
        this.fechapublicacion_row_comprayventa = fechapublicacion_row_comprayventa;
    }

    public String getPrecio_row_comprayventa() {
        return precio_row_comprayventa;
    }

    public void setPrecio_row_comprayventa(String precio_row_comprayventa) {
        this.precio_row_comprayventa = precio_row_comprayventa;
    }

    public String getContacto_comprayventa() {
        return contacto_comprayventa;
    }

    public void setContacto_comprayventa(String contacto_comprayventa) {
        this.contacto_comprayventa = contacto_comprayventa;
    }

    public String getUbicacion_comprayventa() {
        return ubicacion_comprayventa;
    }

    public void setUbicacion_comprayventa(String ubicacion_comprayventa) {
        this.ubicacion_comprayventa = ubicacion_comprayventa;
    }

    public String getDescripcionextra_comprayventa() {
        return descripcionextra_comprayventa;
    }

    public void setDescripcionextra_comprayventa(String descripcionextra_comprayventa) {
        this.descripcionextra_comprayventa = descripcionextra_comprayventa;
    }

    public int getCantidad_comprayventa() {
        return cantidad_comprayventa;
    }

    public void setCantidad_comprayventa(int cantidad_comprayventa) {
        this.cantidad_comprayventa = cantidad_comprayventa;
    }

    public int getVista_comprayventa() {
        return vista_comprayventa;
    }

    public void setVista_comprayventa(int vista_comprayventa) {
        this.vista_comprayventa = vista_comprayventa;
    }
}
