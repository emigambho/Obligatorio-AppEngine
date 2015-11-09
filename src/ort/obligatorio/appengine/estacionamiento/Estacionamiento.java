package ort.obligatorio.appengine.estacionamiento;

import java.util.List;

/**
 * Created by fede on 08/11/2015.
 */
public class Estacionamiento {

    private String nombre;
    private List<Calificacion> calificaciones;
    private List<Parcela> parcelas;
    private double puntaje = -1;

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
}
