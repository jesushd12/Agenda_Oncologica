package com.example.jesus.agendaoncologica;

/**
 * Created by Jesus on 2/1/2016.
 */
public class Alergia {
    private int id;
    private String nombrealergia;

    public Alergia(String nombrealergia, int id) {
        this.nombrealergia = nombrealergia;
        this.id = id;
    }

    public Alergia() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombrealergia() {
        return nombrealergia;
    }

    public void setNombrealergia(String nombrealergia) {
        this.nombrealergia = nombrealergia;
    }
}
