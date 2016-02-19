package com.example.jesus.agendaoncologica;

/**
 * Created by Jesus on 1/31/2016.
 */
public class HistoriaPaciente {
    private String numerohistoria;
    private String condicion;
    private String tipoSangre;
    private String estatura;
    private String peso;


    public HistoriaPaciente() {
    }

    public String getNumerohistoria() {
        return numerohistoria;
    }

    public void setNumerohistoria(String numerohistoria) {
        this.numerohistoria = numerohistoria;
    }

    public HistoriaPaciente(String condicion, String tipoSangre, String estatura, String peso) {
        this.condicion = condicion;
        this.tipoSangre = tipoSangre;
        this.estatura = estatura;
        this.peso = peso;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getEstatura() {
        return estatura;
    }

    public void setEstatura(String estatura) {
        this.estatura = estatura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }
}