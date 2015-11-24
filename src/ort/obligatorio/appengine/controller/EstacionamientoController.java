package ort.obligatorio.appengine.controller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.com.google.api.client.util.StringUtils;
import com.google.appengine.repackaged.com.google.common.base.StringUtil;
import com.sun.org.apache.xpath.internal.operations.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ort.obligatorio.appengine.estacionamiento.Calificacion;
import ort.obligatorio.appengine.estacionamiento.Estacionamiento;
import ort.obligatorio.appengine.estacionamiento.Rol;
import ort.obligatorio.appengine.manager.EstacionamientoManager;
import ort.obligatorio.appengine.util.UsuarioUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.String;
import java.security.Principal;
import java.util.Date;

@Controller
@RequestMapping("/obl")
public class EstacionamientoController {

    @RequestMapping(value="/guardarCambiosAdm", method = RequestMethod.POST)
    public ModelAndView guardarCambiosAdm(HttpServletRequest request, ModelMap model) {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        String nombreEstacionamiento =  request.getParameter("originalName");
        String nuevoNombre = request.getParameter("nombre");
        Double nuevoPuntaje = null;
        try {
            nuevoPuntaje = Double.parseDouble(request.getParameter("puntaje"));
        } catch (NumberFormatException e) {
            ;
        }
        Integer nuevaCapacidad = null;
        try {
            nuevaCapacidad = Integer.parseInt(request.getParameter("capacidad"));
        } catch (NumberFormatException e) {
            ;
        }
        String nuevaApertura = request.getParameter("apertura");
        String nuevoCierre= request.getParameter("cierre");

        EstacionamientoManager.guardarCambiosEstacionamiento(nombreEstacionamiento, nuevoNombre, nuevoPuntaje, nuevaCapacidad, nuevaApertura, nuevoCierre);

        return new ModelAndView("redirect:adm");

    }

    @RequestMapping(value="/editar/{name}", method = RequestMethod.GET)
    public String editarDatosDeEstacionamiento(@PathVariable String name,
                                        HttpServletRequest request, ModelMap model) {

        Estacionamiento estacionamiento = EstacionamientoManager.obtenerEstacionamiento(name);
        model.addAttribute("estacionamiento", estacionamiento);
        model.addAttribute("logout", UserServiceFactory.getUserService().createLogoutURL("/obl/login"));

        return "editar";
    }

    @RequestMapping(value="/agregarComentarioAdm", method = RequestMethod.POST)
    public ModelAndView agregarComentarioAdm(HttpServletRequest request, ModelMap model) {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        String nuevoComentario = request.getParameter("comentario");
        String nombreEstacionamiento =  request.getParameter("originalName");

        String calificacion = request.getParameter("calificacion");
        if (calificacion==null || calificacion.equals("")) {
            calificacion = "0";
        }
        Calificacion nuevaCalificacion = new Calificacion();
        User user = UserServiceFactory.getUserService().getCurrentUser();
        nuevaCalificacion.setUsuario(user.getNickname());
        nuevaCalificacion.setComentario(nuevoComentario);
        nuevaCalificacion.setCalificacion(Integer.parseInt(calificacion));
        EstacionamientoManager.agregarComentarioAEstacionamiento(nombreEstacionamiento, nuevaCalificacion);

        return new ModelAndView("redirect:adm");

    }

    @RequestMapping(value="/adm", method = RequestMethod.GET)
    public String irAEstacionamientoAdm(ModelMap model) {
        Estacionamiento estacionamiento = EstacionamientoManager.obtenerEstacionamientoDefault();
        model.addAttribute("estacionamiento", estacionamiento);
        model.addAttribute("logout", UserServiceFactory.getUserService().createLogoutURL("/obl/login"));

        return "estacionamientoadm";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public void login(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        UserService userService = UserServiceFactory.getUserService();

        String thisURL = req.getRequestURI();

        resp.setContentType("text/html");
        if (req.getUserPrincipal() != null) {
            //ya logueado
            User user = userService.getCurrentUser();
            //obtener usuario de datastore
            //si es administrador, ir a /adm
            //si es usuario, ir a /usr
            if (UsuarioUtil.checkUserRole(user.getEmail(), Rol.ADMINISTRADOR)){
                //resp.getWriter().println("es administrador, <p><a href=\"" + userService.createLogoutURL(thisURL) + "\">Salir</a></p>");
                resp.sendRedirect("/obl/adm");
            } else {
                resp.getWriter().println("es usuario comun, <p><a href=\"" + userService.createLogoutURL(thisURL) + "\">Salir</a></p>");
            }
        } else {
            //enviar a login
            resp.sendRedirect(userService.createLoginURL(thisURL));
        }
    }

}
