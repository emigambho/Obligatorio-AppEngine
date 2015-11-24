package ort.obligatorio.appengine.manager;


import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;
import ort.obligatorio.appengine.estacionamiento.Calificacion;
import ort.obligatorio.appengine.estacionamiento.Estacionamiento;

import java.util.ArrayList;

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
}
