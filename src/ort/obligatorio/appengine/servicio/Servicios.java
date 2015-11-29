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
        ServiciosManager.agregarNotificacionAClave(clave, minutos);
        return true;
    }







    @RequestMapping(value="/prueba", method = RequestMethod.GET)
    public @ResponseBody Estacionamiento editarDatosDeEstacionamiento(HttpServletRequest request, ModelMap model) {
        Estacionamiento e = new Estacionamiento();
        e.setNombre("nombre de estacionamiento json!");
        return e;
    }
//
//    @RequestMapping(value="/pruebalista", method = RequestMethod.GET)
//    public @ResponseBody List<String> lista(HttpServletRequest request, ModelMap model) {
//        List<String> l = new ArrayList<>();
//        l.add("primer string");
//        l.add("segundo string");
//        l.add("tercer string");
//        return l;
//    }

}
