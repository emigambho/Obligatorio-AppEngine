package ort.obligatorio.appengine.manager;


import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;
import ort.obligatorio.appengine.estacionamiento.Calificacion;
import ort.obligatorio.appengine.estacionamiento.Estacionamiento;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EstacionamientoManager {

    public static Estacionamiento obtenerEstacionamiento(String nombre) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query("Estacionamiento");
        query.addFilter("id", Query.FilterOperator.EQUAL, nombre);
        PreparedQuery pq = datastore.prepare(query);
        Entity estacionamientoEnDataStore = pq.asIterable().iterator().next();
        //Entity estacionamientoEnDataStore = pq.asSingleEntity();
        String json = estacionamientoEnDataStore.getProperty("object").toString();
        Estacionamiento estacionamientoDesdeJson = new Gson().fromJson(json, Estacionamiento.class);


        return estacionamientoDesdeJson;
    }

    public static Estacionamiento obtenerEstacionamientoDefault() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query("Estacionamiento");
        query.addFilter("id", Query.FilterOperator.EQUAL, "Estacionamiento Default");
        PreparedQuery pq = datastore.prepare(query);
        Entity estacionamientoEnDataStore = pq.asIterable().iterator().next();
        //Entity estacionamientoEnDataStore = pq.asSingleEntity();
        String json = estacionamientoEnDataStore.getProperty("object").toString();
        Estacionamiento estacionamientoDesdeJson = new Gson().fromJson(json, Estacionamiento.class);


        return estacionamientoDesdeJson;
    }

    public static void agregarComentarioAEstacionamiento(String nombreEstacionamiento, Calificacion calificacion) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query("Estacionamiento");
        query.addFilter("id", Query.FilterOperator.EQUAL, nombreEstacionamiento);
        PreparedQuery pq = datastore.prepare(query);
        Entity estacionamientoEnDataStore = pq.asSingleEntity();
        String json = estacionamientoEnDataStore.getProperty("object").toString();
        Estacionamiento estacionamientoDesdeJson = new Gson().fromJson(json, Estacionamiento.class);
        datastore.delete(estacionamientoEnDataStore.getKey());

        if (estacionamientoDesdeJson.getCalificaciones()==null) {
            estacionamientoDesdeJson.setCalificaciones(new ArrayList<Calificacion>());
        }
        estacionamientoDesdeJson.getCalificaciones().add(calificacion);

        //Key key = KeyFactory.createKey("Estacionamiento", estacionamientoDesdeJson.getNombre());
        String estacionamientoJson = new Gson().toJson(estacionamientoDesdeJson);
        Entity entity= new Entity("Estacionamiento", estacionamientoEnDataStore.getKey());
        entity.setProperty("id", estacionamientoDesdeJson.getNombre());
        entity.setProperty("object", estacionamientoJson);
        datastore.put(entity);
    }

    public static void guardarCambiosEstacionamiento(String nombreEstacionamiento, String nuevoNombre, Double nuevoPuntaje, Integer nuevaCapacidad, String nuevaApertura, String nuevoCierre) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query("Estacionamiento");
        query.addFilter("id", Query.FilterOperator.EQUAL, nombreEstacionamiento);
        PreparedQuery pq = datastore.prepare(query);
        Entity estacionamientoEnDataStore = pq.asSingleEntity();
        String json = estacionamientoEnDataStore.getProperty("object").toString();
        Estacionamiento estacionamientoDesdeJson = new Gson().fromJson(json, Estacionamiento.class);
        datastore.delete(estacionamientoEnDataStore.getKey());

        estacionamientoDesdeJson.setNombre(nuevoNombre);
        estacionamientoDesdeJson.setPuntaje(nuevoPuntaje);
        estacionamientoDesdeJson.setCapacidad(nuevaCapacidad);
        estacionamientoDesdeJson.setHoraDeApertura(nuevaApertura);
        estacionamientoDesdeJson.setHoraDeCierre(nuevoCierre);

        String estacionamientoJson = new Gson().toJson(estacionamientoDesdeJson);
        Entity entity= new Entity("Estacionamiento", estacionamientoEnDataStore.getKey());
        entity.setProperty("id", estacionamientoDesdeJson.getNombre());
        entity.setProperty("object", estacionamientoJson);
        datastore.put(entity);
    }

    public static Estacionamiento obtenerEstacionamientoDeUsuario(String usuario) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Usuario");
        query.addFilter("id", Query.FilterOperator.EQUAL, usuario);
        PreparedQuery pq = datastore.prepare(query);
        Entity userFromDB = pq.asSingleEntity();

        String estacionamiento = userFromDB.getProperty("estacionamiento").toString();

        return obtenerEstacionamiento(estacionamiento);
    }

    public static String obtenerResponsableDeEstacionamiento(String nombreEstacionamiento) {
        Estacionamiento e = obtenerEstacionamiento(nombreEstacionamiento);
        return e.getMailResponsable();
    }

    public static List<String> obtenerNombreDeTodos() {
        List<String> todos = new ArrayList<>();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Estacionamiento");
        query.addProjection(new PropertyProjection("id", String.class));
        PreparedQuery pq = datastore.prepare(query);
        Iterator<Entity> i = pq.asIterator();
        while(i.hasNext()) {
            Entity entity = i.next();
            String id = entity.getProperty("id").toString();
            //todos.add(new Gson().fromJson(json, Estacionamiento.class));
            todos.add(id);
        }

        return todos;
    }

    public static List<Estacionamiento> obtenerTodos() {
        List<Estacionamiento> todos = new ArrayList<>();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Estacionamiento");
        PreparedQuery pq = datastore.prepare(query);
        Iterator<Entity> i = pq.asIterator();
        while(i.hasNext()) {
            Entity entity = i.next();
            String json = entity.getProperty("object").toString();
            todos.add(new Gson().fromJson(json, Estacionamiento.class));
        }

        return todos;
    }
}
