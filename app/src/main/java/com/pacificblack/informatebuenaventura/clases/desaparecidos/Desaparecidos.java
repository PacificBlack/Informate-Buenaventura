package com.pacificblack.informatebuenaventura.clases.desaparecidos;

import android.media.Image;

public class Desaparecidos {

    String titulo,                                                          // Titulo del row
            descripcion_corta,                                              // Descripción para el row
            parrafo_1;                                                      // Espacios para escribir algo acerca del desaparecido


    Image imagen1,imagen2,imagen3;                                          // Imagenes del desaparecido


    String  fecha_publicacion,fecha_desaparicion,                           // Fecha de la publicación y fecha de desaparicion
            nombrecompleto,apodo,                                           // Identificación de la persona
            ultimolugarvisto,dias_de_desaparecido,camisa,pantalon,zapatos,accesorios,       // Datos para facilitar la busqueda
            enfermedad,discapacidad;                                        // Condiciones o discapacidades de salud y/o bienestar


    int     numerodecontacto1,numerodecontacto2,redesocial1,redsocial2,     // Medios de Contactos
            recompensa;                                                     // Pago por el desaparecido


    public Desaparecidos() {
    }

    public Desaparecidos(String titulo, String descripcion_corta, String parrafo_1, Image imagen1, Image imagen2, Image imagen3, String fecha_publicacion, String fecha_desaparicion, String nombrecompleto, String apodo, String ultimolugarvisto, String dias_de_desaparecido, String camisa, String pantalon, String zapatos, String accesorios, String enfermedad, String discapacidad, int numerodecontacto1, int numerodecontacto2, int redesocial1, int redsocial2, int recompensa) {
        this.titulo = titulo;
        this.descripcion_corta = descripcion_corta;
        this.parrafo_1 = parrafo_1;
        this.imagen1 = imagen1;
        this.imagen2 = imagen2;
        this.imagen3 = imagen3;
        this.fecha_publicacion = fecha_publicacion;
        this.fecha_desaparicion = fecha_desaparicion;
        this.nombrecompleto = nombrecompleto;
        this.apodo = apodo;
        this.ultimolugarvisto = ultimolugarvisto;
        this.dias_de_desaparecido = dias_de_desaparecido;
        this.camisa = camisa;
        this.pantalon = pantalon;
        this.zapatos = zapatos;
        this.accesorios = accesorios;
        this.enfermedad = enfermedad;
        this.discapacidad = discapacidad;
        this.numerodecontacto1 = numerodecontacto1;
        this.numerodecontacto2 = numerodecontacto2;
        this.redesocial1 = redesocial1;
        this.redsocial2 = redsocial2;
        this.recompensa = recompensa;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion_corta() {
        return descripcion_corta;
    }

    public void setDescripcion_corta(String descripcion_corta) {
        this.descripcion_corta = descripcion_corta;
    }

    public String getParrafo_1() {
        return parrafo_1;
    }

    public void setParrafo_1(String parrafo_1) {
        this.parrafo_1 = parrafo_1;
    }

    public Image getImagen1() {
        return imagen1;
    }

    public void setImagen1(Image imagen1) {
        this.imagen1 = imagen1;
    }

    public Image getImagen2() {
        return imagen2;
    }

    public void setImagen2(Image imagen2) {
        this.imagen2 = imagen2;
    }

    public Image getImagen3() {
        return imagen3;
    }

    public void setImagen3(Image imagen3) {
        this.imagen3 = imagen3;
    }

    public String getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(String fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public String getFecha_desaparicion() {
        return fecha_desaparicion;
    }

    public void setFecha_desaparicion(String fecha_desaparicion) {
        this.fecha_desaparicion = fecha_desaparicion;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getUltimolugarvisto() {
        return ultimolugarvisto;
    }

    public void setUltimolugarvisto(String ultimolugarvisto) {
        this.ultimolugarvisto = ultimolugarvisto;
    }

    public String getDias_de_desaparecido() {
        return dias_de_desaparecido;
    }

    public void setDias_de_desaparecido(String dias_de_desaparecido) {
        this.dias_de_desaparecido = dias_de_desaparecido;
    }

    public String getCamisa() {
        return camisa;
    }

    public void setCamisa(String camisa) {
        this.camisa = camisa;
    }

    public String getPantalon() {
        return pantalon;
    }

    public void setPantalon(String pantalon) {
        this.pantalon = pantalon;
    }

    public String getZapatos() {
        return zapatos;
    }

    public void setZapatos(String zapatos) {
        this.zapatos = zapatos;
    }

    public String getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(String accesorios) {
        this.accesorios = accesorios;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(String discapacidad) {
        this.discapacidad = discapacidad;
    }

    public int getNumerodecontacto1() {
        return numerodecontacto1;
    }

    public void setNumerodecontacto1(int numerodecontacto1) {
        this.numerodecontacto1 = numerodecontacto1;
    }

    public int getNumerodecontacto2() {
        return numerodecontacto2;
    }

    public void setNumerodecontacto2(int numerodecontacto2) {
        this.numerodecontacto2 = numerodecontacto2;
    }

    public int getRedesocial1() {
        return redesocial1;
    }

    public void setRedesocial1(int redesocial1) {
        this.redesocial1 = redesocial1;
    }

    public int getRedsocial2() {
        return redsocial2;
    }

    public void setRedsocial2(int redsocial2) {
        this.redsocial2 = redsocial2;
    }

    public int getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(int recompensa) {
        this.recompensa = recompensa;
    }
}
