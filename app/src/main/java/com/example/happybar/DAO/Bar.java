package com.example.happybar.DAO;

import java.util.ArrayList;

public class Bar {

    private double latitud;
    private double longitud;
    private String nombre;
    private ArrayList<Oferta> ofertas;

    public Bar(double latitud, double longitud, String nombre, ArrayList<Oferta> ofertas) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
        this.ofertas = ofertas;
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

    @Override
    public String toString() {
        return "Bar{" +
                "latitud=" + latitud +
                ", longitud=" + longitud +
                ", nombre='" + nombre + '\'' +
                ", ofertas=" + ofertas +
                '}';
    }
}
