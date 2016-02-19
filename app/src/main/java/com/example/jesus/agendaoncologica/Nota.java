package com.example.jesus.agendaoncologica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by Jesus on 2/1/2016.
 */
public class Nota {
    private int id;
    private Date fecha;
    private String titulo;
    private String nota;
    private int idExamenAsociado;
    final private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public Nota() {
        idExamenAsociado=-1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String obtenerFechaFormateada(){
        return formatter.format(getFecha());
    }
    public void setFechaFormateada(String fecha){
        try {
            setFecha(formatter.parse(fecha));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getIdExamenAsociado() {
        return idExamenAsociado;
    }

    public void setIdExamenAsociado(int idExamenAsociado) {
        this.idExamenAsociado = idExamenAsociado;
    }
}
