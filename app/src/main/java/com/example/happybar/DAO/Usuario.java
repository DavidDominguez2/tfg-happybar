package com.example.happybar.DAO;

public class Usuario {

    private int id;
    private String nombre;
    private String correo;
    private String pwd;
    private boolean verified;

    public Usuario(int id, String nombre, String correo, String pwd, boolean verified) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.pwd = pwd;
        this.verified = verified;
    }

    public Usuario(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", pwd='" + pwd + '\'' +
                ", verified=" + verified +
                '}';
    }
}
