package com.example.happybar.DAO;

import java.util.ArrayList;

public class Bar {

    private double latitud;
    private double longitud;
    private String nombre;
    private ArrayList<Oferta> ofertas;
    private String rango;

    public Bar(double latitud, double longitud, String nombre, ArrayList<Oferta> ofertas, String rango) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
        this.ofertas = ofertas;
        this.rango = rango;
    }
    public Bar(){
        super();
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Oferta> getOfertas() {
        return ofertas;
    }

    public void setOfertas(ArrayList<Oferta> ofertas) {
        this.ofertas = ofertas;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    @Override
    public String toString() {
        return "Bar{" +
                "latitud=" + latitud +
                ", longitud=" + longitud +
                ", nombre='" + nombre + '\'' +
                ", ofertas=" + ofertas +
                ", rango='" + rango + '\'' +
                '}';
    }
}
