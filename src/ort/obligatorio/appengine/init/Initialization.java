package ort.obligatorio.appengine.init;

import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;
import ort.obligatorio.appengine.estacionamiento.Calificacion;
import ort.obligatorio.appengine.estacionamiento.Estacionamiento;
import ort.obligatorio.appengine.estacionamiento.Parcela;
import ort.obligatorio.appengine.estacionamiento.Rol;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fede on 21/11/2015.
 */
public class Initialization {

    public void init() {

        initEstacionamientos();
        initUsuarios();
    }

    private void initEstacionamientos() {
        //borrar todos.
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Estacionamiento");
        PreparedQuery pq = datastore.prepare(query);
        Iterable<Entity> i = pq.asIterable();
        List<Key> list = new ArrayList();
        for (Entity e : i) {
            list.add(e.getKey());
        }
        datastore.delete(list);

        //Crear default
        Estacionamiento e = new Estacionamiento();
        e.setNombre("Estacionamiento Default");
        e.setPuntaje(4d);
        List<Parcela> parcelas = new ArrayList<Parcela>();
        Parcela parcela = new Parcela();
        parcela.setDescripcion("Parcela 1");
        parcela.setId("1");
        parcelas.add(parcela);
        parcela = new Parcela();
        parcela.setDescripcion("Parcela 2");
        parcela.setId("2");
        parcelas.add(parcela);
        e.setParcelas(parcelas);
        List<Calificacion> calificaciones = new ArrayList<Calificacion>();
        Calificacion calificacion = new Calificacion();
        calificacion.setCalificacion(5);
        calificacion.setComentario("Es un excelente estacionamiento");
        calificacion.setUsuario("Federico");
        calificaciones.add(calificacion);
        calificacion = new Calificacion();
        calificacion.setCalificacion(3);
        calificacion.setComentario("Es un estacionamiento mediocre");
        calificacion.setUsuario("Emiliano");
        calificaciones.add(calificacion);
        e.setCalificaciones(calificaciones);
        e.setCapacidad(2);
        e.setHoraDeApertura("7:00");
        e.setHoraDeCierre("23:30");

        Key key = KeyFactory.createKey("Estacionamiento", e.getNombre());
        String estacionamientoJson = new Gson().toJson(e);
        Entity entityEstacionamiento = new Entity("Estacionamiento", key);
        entityEstacionamiento.setProperty("id", e.getNombre());
        entityEstacionamiento.setProperty("object", estacionamientoJson);
        datastore.put(entityEstacionamiento);
    }

    private void initUsuarios() {
        /*DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = KeyFactory.createKey("Usuario", "fbarbieri@gmail.com");
        Entity user = new Entity("Usuario", key);
        user.setProperty("id", "fbarbieri@gmail.com");
        user.setProperty("rol", Rol.ADMINISTRADOR.toString());
        datastore.put(user);*/
    }
}
