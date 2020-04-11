package com.pacificblack.informatebuenaventura.clases.noticias;

public class UltimaHora {

    private String titulo_row_ultimahora,descripcion_row_ultimahora,fechapublicacion_row_ultimahora;
    private String imagen_row_ultimahora;
    private int vistas_row_ultimahora,id_ultimahora;


    public UltimaHora() {
    }

    public UltimaHora(String titulo_row_ultimahora, String descripcion_row_ultimahora, String fechapublicacion_row_ultimahora, String imagen_row_ultimahora, int vistas_row_ultimahora, int id_ultimahora) {
        this.titulo_row_ultimahora = titulo_row_ultimahora;
        this.descripcion_row_ultimahora = descripcion_row_ultimahora;
        this.fechapublicacion_row_ultimahora = fechapublicacion_row_ultimahora;
        this.imagen_row_ultimahora = imagen_row_ultimahora;
        this.vistas_row_ultimahora = vistas_row_ultimahora;
        this.id_ultimahora= id_ultimahora;
    }

    public String getImagen_row_ultimahora() {
        return imagen_row_ultimahora;
    }

    public void setImagen_row_ultimahora(String imagen_row_ultimahora) {
        this.imagen_row_ultimahora = imagen_row_ultimahora;
    }

    public int getId_ultimahora() {
        return id_ultimahora;
    }

    public void setId_ultimahora(int id_ultimahora) {
        this.id_ultimahora = id_ultimahora;
    }

    public String getTitulo_row_ultimahora() {
        return titulo_row_ultimahora;
    }

    public void setTitulo_row_ultimahora(String titulo_row_ultimahora) {
        this.titulo_row_ultimahora = titulo_row_ultimahora;
    }

    public String getDescripcion_row_ultimahora() {
        return descripcion_row_ultimahora;
    }

    public void setDescripcion_row_ultimahora(String descripcion_row_ultimahora) {
        this.descripcion_row_ultimahora = descripcion_row_ultimahora;
    }

    public String getFechapublicacion_row_ultimahora() {
        return fechapublicacion_row_ultimahora;
    }

    public void setFechapublicacion_row_ultimahora(String fechapublicacion_row_ultimahora) {
        this.fechapublicacion_row_ultimahora = fechapublicacion_row_ultimahora;
    }

    public int getVistas_row_ultimahora() {
        return vistas_row_ultimahora;
    }

    public void setVistas_row_ultimahora(int vistas_row_ultimahora) {
        this.vistas_row_ultimahora = vistas_row_ultimahora;
    }
}
