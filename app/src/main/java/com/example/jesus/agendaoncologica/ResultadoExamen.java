package com.example.jesus.agendaoncologica;

/**
 * Created by Jesus on 2/2/2016.
 */
public class ResultadoExamen {
    private Examen examen;
    private AnalisisExamen analisisExamen;
    private String valor="Sin definir";

    public ResultadoExamen(AnalisisExamen analisisExamen, Examen examen, String valor) {
        this.analisisExamen = analisisExamen;
        this.examen = examen;
        this.valor = valor;
    }

    public ResultadoExamen() {
    }

    public AnalisisExamen getAnalisisExamen() {
        return analisisExamen;
    }

    public void setAnalisisExamen(AnalisisExamen analisisExamen) {
        this.analisisExamen = analisisExamen;
    }

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
