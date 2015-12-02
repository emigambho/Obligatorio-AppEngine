package ort.obligatorio.appengine.servicio;

import com.google.appengine.api.users.UserServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ort.obligatorio.appengine.estacionamiento.Estacionamiento;
import ort.obligatorio.appengine.manager.EstacionamientoManager;
import ort.obligatorio.appengine.servicio.manager.ServiciosManager;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/servicios")
public class Servicios {

//    POST
//    inicioEnEstacionamiento(String clave)
//    avisoDeRetiroProximo(Integer minutos)
//    calificarEstacionamiento(String idEstacionamiento, int puntaje, String comentario)
//
//    GET
//    obtenerNotificaciones(String clave):List<String> (notificaciones medio planas)
//    listaDeEstacionamientos()

    @RequestMapping(value="/inicio", method = RequestMethod.POST)
    public @ResponseBody boolean inicioEnEstacionamiento(HttpServletRequest request) {
        String clave = request.getParameter("clave");
        ServiciosManager.guardarClaveParaNotificaciones(clave);
        return true;
    }

    @RequestMapping(value="/aviso", method = RequestMethod.POST)
    public @ResponseBody boolean avisoDeRetiro(HttpServletRequest request) {
        String duracion = request.getParameter("minutos");
        String clave = request.getParameter("clave");
        int minutos = 0;
        try {
            minutos = Integer.parseInt(duracion);
        } catch (Exception e) {
            return false;
        }
        ServiciosManager.agregarAvisoAClave(clave, minutos);
        return true;
    }

    @RequestMapping(value="/calificar", method = RequestMethod.POST)
    public @ResponseBody boolean calificarEstacionamiento(HttpServletRequest request) {
        String estacionamiento = request.getParameter("idEstacionamiento");
        String puntaje = request.getParameter("puntaje");
        String comentario = request.getParameter("comentario");
        String usuario = request.getParameter("usuario");

        boolean resultado = ServiciosManager.agregarCalificacion(estacionamiento, puntaje, comentario, usuario);

        return resultado;
    }


    @RequestMapping(value="/estacionamientos", method = RequestMethod.GET)
    public @ResponseBody List<Estacionamiento> listaDeEstacionamientos(HttpServletRequest request) {
        List<Estacionamiento> lista = ServiciosManager.obtenerEstacionamientos();
        if (lista==null) {
            lista = new ArrayList<>();
        }
        return lista;
    }

    @RequestMapping(value="/notificaciones/{clave}", method = RequestMethod.GET)
    public @ResponseBody List<String> obtenerNotificaciones(@PathVariable String clave, HttpServletRequest request) {
        List<String> notificaciones = new ArrayList<>();

        notificaciones = ServiciosManager.obtenerNotificacionesParaClave(clave);

        return notificaciones;
    }

    @RequestMapping(value="/prueba", method = RequestMethod.GET)
    public @ResponseBody Estacionamiento editarDatosDeEstacionamiento(HttpServletRequest request, ModelMap model) {
        Estacionamiento e = new Estacionamiento();
        e.setNombre("nombre de estacionamiento json!");
        return e;
    }

}
