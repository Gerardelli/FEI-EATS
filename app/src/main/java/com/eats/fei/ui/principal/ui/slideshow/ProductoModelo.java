package com.eats.fei.ui.principal.ui.slideshow;

import android.net.Uri;

public class ProductoModelo {

    private String nombre, precio, descripcion;
    private int imgProducto;

    public ProductoModelo(){

    }
    ProductoModelo(String nombre, String precio, String descripcion, int imgProducto){
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imgProducto = imgProducto;
    }

    public ProductoModelo(String nombre, String precio, String descripcion, Uri photoUrl) {
    }

    public ProductoModelo(String nombre, String precio, String descripcion) {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    int getImgProducto() {
        return imgProducto;
    }

    public void setImgProducto(int imgProducto) {
        this.imgProducto = imgProducto;
    }

    String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
