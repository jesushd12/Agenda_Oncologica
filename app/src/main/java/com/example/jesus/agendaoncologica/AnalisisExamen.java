package com.example.jesus.agendaoncologica;

/**
 * Created by Jesus on 2/2/2016.
 */
public class AnalisisExamen {
    private TipoExamen tipoExamen;
    private String analisis;

    public AnalisisExamen() {
    }

    public AnalisisExamen(String analisis, TipoExamen tipoExamen) {
        this.analisis = analisis;
        this.tipoExamen = tipoExamen;
    }

    public String getAnalisis() {
        return analisis;
    }

    public void setAnalisis(String analisis) {
        this.analisis = analisis;
    }

    public TipoExamen getTipoExamen() {
        return tipoExamen;
    }

    public void setTipoExamen(TipoExamen tipoExamen) {
        this.tipoExamen = tipoExamen;
    }


}
