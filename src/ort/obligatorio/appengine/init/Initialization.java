package ort.obligatorio.appengine.init;

import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;
import ort.obligatorio.appengine.estacionamiento.Calificacion;
import ort.obligatorio.appengine.estacionamiento.Estacionamiento;
import ort.obligatorio.appengine.estacionamiento.Parcela;
import ort.obligatorio.appengine.estacionamiento.Rol;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
        e.setLatitud(-34.906540);
        e.setLongitud(-56.200819);
        e.setMailResponsable("appenlanube.adm@gmail.com");
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

        Estacionamiento e2 = new Estacionamiento();
        e2.setNombre("Estacionamiento Dos");
        e2.setPuntaje(4d);
        e2.setLatitud(-34.894440);
        e2.setLongitud(-56.167600);
        e2.setMailResponsable("appenlanube.adm@gmail.com");
        List<Parcela> parcelas2 = new ArrayList<Parcela>();
        Parcela parcela2 = new Parcela();
        parcela2.setDescripcion("Parcela 1");
        parcela2.setId("1");
        parcelas2.add(parcela2);
        e2.setParcelas(parcelas2);
        List<Calificacion> calificaciones2 = new ArrayList<Calificacion>();
        Calificacion calificacion2 = new Calificacion();
        calificacion2.setCalificacion(5);
        calificacion2.setComentario("Es un excelente estacionamiento");
        calificacion2.setUsuario("Federico");
        calificaciones2.add(calificacion2);
        e2.setCalificaciones(calificaciones2);
        e2.setCapacidad(3);
        e2.setHoraDeApertura("6:00");
        e2.setHoraDeCierre("22:00");

        Key key2 = KeyFactory.createKey("Estacionamiento", e2.getNombre());
        String estacionamientoJson2 = new Gson().toJson(e2);
        Entity entityEstacionamiento2 = new Entity("Estacionamiento", key2);
        entityEstacionamiento2.setProperty("id", e2.getNombre());
        entityEstacionamiento2.setProperty("object", estacionamientoJson2);
        datastore.put(entityEstacionamiento2);
    }

    private void initUsuarios() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Usuario");
        PreparedQuery pq = datastore.prepare(query);
        Iterable<Entity> i = pq.asIterable();
        List<Key> list = new ArrayList();
        for (Entity e : i) {
            list.add(e.getKey());
        }
        datastore.delete(list);

        Key key = KeyFactory.createKey("Usuario", "fbarbieri@gmail.com");
        Entity user = new Entity("Usuario", key);
        user.setProperty("id", "fbarbieri@gmail.com");
        user.setProperty("rol", Rol.ADMINISTRADOR.toString());
        user.setProperty("estacionamiento", "Estacionamiento Default");
        datastore.put(user);

        key = KeyFactory.createKey("Usuario", "emigambho@gmail.com");
        user = new Entity("Usuario", key);
        user.setProperty("id", "emigambho@gmail.com");
        user.setProperty("rol", Rol.ADMINISTRADOR.toString());
        user.setProperty("estacionamiento", "Estacionamiento Default");
        datastore.put(user);

        key = KeyFactory.createKey("Usuario", "appenlanube.adm@gmail.com");
        user = new Entity("Usuario", key);
        user.setProperty("id", "appenlanube.adm@gmail.com");
        user.setProperty("rol", Rol.ADMINISTRADOR.toString());
        user.setProperty("estacionamiento", "Estacionamiento Default");
        datastore.put(user);

        key = KeyFactory.createKey("Usuario", "appenlanube.usuario@gmail.com");
        user = new Entity("Usuario", key);
        user.setProperty("id", "appenlanube.usuario@gmail.com");
        user.setProperty("rol", Rol.USUARIO.toString());
        datastore.put(user);
    }
}
