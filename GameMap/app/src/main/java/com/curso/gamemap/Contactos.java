package com.curso.gamemap;

public class Contactos {

    private int id;
    private String nombre;
    private String telefono;
    private String email;
    private String team;



    public Contactos(int id, String nombre, String telefono, String email, String team) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.team = team;
    }



    public int getID() {
        return id;
    }
    public void setID(int id) {
        this.id = id;
    }


    public String getNOMBRE() {
        return nombre;
    }
    public void setNOMBRE(String nombre) {
        this.nombre = nombre;
    }


    public String getTELEFONO() {
        return telefono;
    }
    public void setTELEFONO(String telefono) {
        this.telefono = telefono;
    }


    public String getEMAIL() {
        return email;
    }
    public void setEMAIL(String email) {
        this.email = email;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
