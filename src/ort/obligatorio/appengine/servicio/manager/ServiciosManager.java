package ort.obligatorio.appengine.servicio.manager;

import com.google.appengine.api.datastore.*;
import ort.obligatorio.appengine.estacionamiento.Rol;

import java.util.ArrayList;
import java.util.List;

public class ServiciosManager {
    public static void guardarClaveParaNotificaciones(String clave) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("ClaveNotificacion");
//        PreparedQuery pq = datastore.prepare(query);
//        Iterable<Entity> i = pq.asIterable();
//        List<Key> list = new ArrayList();
//        for (Entity e : i) {
//            list.add(e.getKey());
//        }
//        datastore.delete(list);

        Key key = KeyFactory.createKey("ClaveNotificacion", clave);
        Entity entity = new Entity("ClaveNotificacion", key);
        entity.setProperty("clave", clave);
        datastore.put(entity);

        /**
         * TODO agregar notificaciones para pruebas/defensa
         */
    }

    public static void agregarNotificacionAClave(String clave, int minutos) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("ClaveAviso");
        Key key = KeyFactory.createKey("ClaveAviso", clave+minutos);
        Entity entity = new Entity("ClaveAviso", key);
        entity.setProperty("clave", clave);
        entity.setProperty("aviso", minutos);
        datastore.put(entity);

    }
}
