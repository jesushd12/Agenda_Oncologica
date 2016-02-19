package com.example.jesus.agendaoncologica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by Jesus on 2/1/2016.
 */
public class Tratamiento {
    private int id;
    private String fecha;
    private String tipo;
    private String hora;

    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
    Calendar c = Calendar.getInstance();

    public Tratamiento(int id, String fecha, String tipo) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.hora = "Sin definir";
    }

    public Tratamiento() {
        this.hora = "Sin definir";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int  getMes(){
        try {
            c.setTime(formato.parse(getFecha()));
            return c.MONTH-1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public Date getDate(){
        try {
            return formato.parse(getFecha());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getAno(){
        try {
            c.setTime(formato.parse(getFecha()));
            return c.YEAR;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
