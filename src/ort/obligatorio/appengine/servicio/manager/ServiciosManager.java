package ort.obligatorio.appengine.servicio.manager;

import com.google.appengine.api.datastore.*;
import ort.obligatorio.appengine.controller.EstacionamientoController;
import ort.obligatorio.appengine.estacionamiento.Calificacion;
import ort.obligatorio.appengine.estacionamiento.Estacionamiento;
import ort.obligatorio.appengine.estacionamiento.Rol;
import ort.obligatorio.appengine.manager.EstacionamientoManager;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;

public class ServiciosManager {
    public static void guardarClaveParaNotificaciones(String clave) {
////        PreparedQuery pq = datastore.prepare(query);
////        Iterable<Entity> i = pq.asIterable();
////        List<Key> list = new ArrayList();
////        for (Entity e : i) {
////            list.add(e.getKey());
////        }
////        datastore.delete(list);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Clave");
        Key key = KeyFactory.createKey("Clave", clave);
        Entity entity = new Entity("Clave", key);
        entity.setProperty("clave", clave);
        datastore.put(entity);

        /**
         * TODO agregar notificaciones para pruebas/defensa
         */
    }

    public static void agregarAvisoAClave(String clave, int minutos) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Clave");
        query.addFilter("clave", Query.FilterOperator.EQUAL, clave);
        PreparedQuery pq = datastore.prepare(query);
        Entity entity = pq.asSingleEntity();
        List<String> lista = null;
        if (entity.getProperty("avisos")==null) {
            lista = new ArrayList<>();
        } else {
            lista = (List<String>) entity.getProperty("avisos");
        }
        lista.add(String.valueOf(minutos));
        entity.setProperty("avisos", lista);

        Key key = KeyFactory.createKey("Clave", clave);
        datastore.put(entity);

    }

    public static boolean agregarCalificacion(String estacionamiento, String puntaje, String comentario, String usuario) {
        try {
            Calificacion calificacion = new Calificacion();
            calificacion.setCalificacion(Integer.parseInt(puntaje));
            calificacion.setComentario(comentario);
            calificacion.setUsuario(usuario);
            EstacionamientoManager.agregarComentarioAEstacionamiento(estacionamiento, calificacion);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static List<String> obtenerNotificacionesParaClave(String clave) {
        List<String> notificaciones = new ArrayList<>();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Clave");
        query.addFilter("clave", Query.FilterOperator.EQUAL, clave);
        PreparedQuery pq = datastore.prepare(query);
        Iterator<Entity> i = pq.asIterator();
        while (i.hasNext()) {
            Entity entity = i.next();
            if (entity.getProperty("notificaciones")!=null) {
                notificaciones.addAll((List<String>) entity.getProperty("notificaciones"));
            }
        }
        return notificaciones;
    }

    public static List<Estacionamiento> obtenerEstacionamientos() {
        return EstacionamientoManager.obtenerTodos();
    }


}
