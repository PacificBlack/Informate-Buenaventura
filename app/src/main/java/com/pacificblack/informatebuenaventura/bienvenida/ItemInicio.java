package com.pacificblack.informatebuenaventura.bienvenida;

public class ItemInicio {
    String Tituli,Descripcion;
    int imagen;

    public ItemInicio(String tituli, String descripcion, int imagen) {
        Tituli = tituli;
        Descripcion = descripcion;
        this.imagen = imagen;
    }

    public ItemInicio() {
    }

    public String getTituli() {
        return Tituli;
    }

    public void setTituli(String tituli) {
        Tituli = tituli;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
