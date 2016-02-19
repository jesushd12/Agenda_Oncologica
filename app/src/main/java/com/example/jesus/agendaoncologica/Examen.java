package com.example.jesus.agendaoncologica;

import java.util.Date;

/**
 * Created by Jesus on 2/2/2016.
 */
public class Examen {
    private int id;
    private Date fecha;
    private int idTratamientoAsociado;

    public Examen() {
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTratamientoAsociado() {
        return idTratamientoAsociado;
    }

    public void setIdTratamientoAsociado(int idTratamientoAsociado) {
        this.idTratamientoAsociado = idTratamientoAsociado;
    }
}
