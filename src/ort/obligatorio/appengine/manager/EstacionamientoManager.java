package ort.obligatorio.appengine.manager;


import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;
import ort.obligatorio.appengine.estacionamiento.Estacionamiento;

public class EstacionamientoManager {

    public static Estacionamiento obtenerEstacionamientoDefault() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query("Estacionamiento");
        query.addFilter("id", Query.FilterOperator.EQUAL, "Estacionamiento Default");
        PreparedQuery pq = datastore.prepare(query);
        Entity estacionamientoEnDataStore = pq.asSingleEntity();
        String json = estacionamientoEnDataStore.getProperty("object").toString();
        Estacionamiento estacionamientoDesdeJson = new Gson().fromJson(json, Estacionamiento.class);


        return estacionamientoDesdeJson;
    }

}
