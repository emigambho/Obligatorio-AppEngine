package ort.obligatorio.appengine.estacionamiento;

/**
 * Created by fede on 08/11/2015.
 */
public class Calificacion {

    private int calificacion;
    private String comentario;

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * verificar si va a ser siempre string...
     */
    private String usuario;



}
