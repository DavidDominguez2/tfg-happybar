package com.example.happybar.DAO;

import java.io.Serializable;

public class Oferta implements Serializable {

    private String descripcion;
    private String img;
    private String nombre;
    private String precio;
    private String rango;

    public Oferta(String descripcion, String img, String nombre, String precio, String rango) {
        this.descripcion = descripcion;
        this.img = img;
        this.nombre = nombre;
        this.precio = precio;
        this.rango = rango;
    }

    public Oferta(){
        super();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "descripcion='" + descripcion + '\'' +
                ", img='" + img + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", rango='" + rango + '\'' +
                '}';
    }
}
