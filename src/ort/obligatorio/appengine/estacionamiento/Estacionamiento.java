package ort.obligatorio.appengine.estacionamiento;

import java.util.List;

public class Estacionamiento {

    private String nombre;
    private List<Calificacion> calificaciones;
    private List<Parcela> parcelas;
    private double puntaje = -1;
    private int capacidad;
    private String horaDeApertura;
    private String horaDeCierre;
    private double latitud;
    private double longitud;
    private String mailResponsable;


    public String getMailResponsable() {
        return mailResponsable;
    }

    public void setMailResponsable(String mailResponsable) {
        this.mailResponsable = mailResponsable;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getHoraDeApertura() {
        return horaDeApertura;
    }

    public void setHoraDeApertura(String horaDeApertura) {
        this.horaDeApertura = horaDeApertura;
    }

    public String getHoraDeCierre() {
        return horaDeCierre;
    }

    public void setHoraDeCierre(String horaDeCierre) {
        this.horaDeCierre = horaDeCierre;
    }
    //ubicación?

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<Parcela> parcelas) {
        this.parcelas = parcelas;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
