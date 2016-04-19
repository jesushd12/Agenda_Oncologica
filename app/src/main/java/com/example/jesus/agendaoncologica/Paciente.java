package com.example.jesus.agendaoncologica;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jesus on 1/31/2016.
 */
public class Paciente {
    private String nombre;
    private String apellido;
    private String cedula;
    private String sexo;
    private Date fechaNacimiento;
    private String lugarNacimiento;
    private String email;
    private String telefonocontacto1;
    private String telefonocontacto2;
    private HistoriaPaciente historia;
    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");



    public Paciente() {
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public HistoriaPaciente getHistoria() {
        return historia;
    }

    public void setHistoria(HistoriaPaciente historia) {
        this.historia = historia;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefonocontacto1() {
        return telefonocontacto1;
    }

    public void setTelefonocontacto1(String telefonocontacto1) {
        this.telefonocontacto1 = telefonocontacto1;
    }

    public String getTelefonocontacto2() {
        return telefonocontacto2;
    }

    public void setTelefonocontacto2(String telefonocontacto2) {
        this.telefonocontacto2 = telefonocontacto2;
    }

    public String obtenerFechaNacimiento(){
        return formato.format(fechaNacimiento);
    }



    public String obtenerNumeroHistoria(){return  historia.getNumerohistoria();};
    public String obtenerCondicion(){
        return historia.getCondicion();
    }

    public String obtenerTipoSangre(){
        return  historia.getTipoSangre();
    }

    public String obtenerEstatura(){
        return historia.getEstatura();
    }
    public String obtenerPeso(){
        return historia.getPeso();
    }

    public int obtenerEdad(){
        int edad;
        Calendar c = Calendar.getInstance();
        Calendar aux = Calendar.getInstance();
        aux.setTime(getFechaNacimiento());
        edad = c.YEAR - aux.YEAR;

        return edad;
    }
}
