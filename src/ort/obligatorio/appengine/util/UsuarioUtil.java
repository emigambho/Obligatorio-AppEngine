package ort.obligatorio.appengine.util;

import com.google.appengine.api.datastore.*;
import ort.obligatorio.appengine.estacionamiento.Rol;


public class UsuarioUtil {

    public static boolean checkUserRole(String user, Rol rol) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Usuario");
        query.addFilter("id", Query.FilterOperator.EQUAL, user);
        query.addFilter("rol", Query.FilterOperator.EQUAL, rol.toString());
        PreparedQuery pq = datastore.prepare(query);
        Entity userFromDB = pq.asSingleEntity();

        return userFromDB!=null;
    }

}
