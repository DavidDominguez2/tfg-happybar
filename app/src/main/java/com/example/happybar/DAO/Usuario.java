package com.example.happybar.DAO;

import java.util.ArrayList;

public class Usuario {

    private String nombre;
    private String apellido;
    private String correo;
    private ArrayList<String> favoritos;

    public Usuario(String nombre, String apellido, String correo, ArrayList<String> favoritos) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.favoritos = favoritos;
    }

    public Usuario(){
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public ArrayList<String> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(ArrayList<String> favoritos) {
        this.favoritos = favoritos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", favoritos=" + favoritos +
                '}';
    }
}
