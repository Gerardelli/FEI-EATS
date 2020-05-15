package com.eats.fei.ui.registrar;

public class Producto {
    //Atributos
    public String productoid;
    public String nombre;
    public  String precio;
    public String descripcion;
    public String categoria;

    public Producto(){
        //Constructor default
    }

    //Contructor con parámetros
    public Producto(String productoid, String nombre, String precio, String descripcion, String categoria){
        this.productoid = productoid;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    //Setters y getters
    public String getProductoid(){return productoid;}

    public void setProductoid(String productoid) {
        this.productoid = productoid;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
