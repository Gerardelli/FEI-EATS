package com.eats.fei.ui.principal.ui.slideshow;

import android.net.Uri;

public class Mensaje {
    private static String logo;
    private String nombre;
    private String precio;
    private String descripcion;
    //private String logo;
    public Mensaje(String nombre){

    }
    public Mensaje(String nombre, String precio, String descripcion){
        this.nombre = nombre;
        this.precio= precio;
        //this.logo=logo;
        this.descripcion = descripcion;


    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public static String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescripcion() {
        return descripcion;
    }
//---
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
