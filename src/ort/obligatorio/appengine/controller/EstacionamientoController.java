package ort.obligatorio.appengine.controller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
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

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.String;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

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
        model.addAttribute("logout", UserServiceFactory.getUserService().createLogoutURL("/"));

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
        String usuario = obtenerUsuario();
        usuario = usuario.substring(0, usuario.lastIndexOf("@"));
        nuevaCalificacion.setUsuario(usuario);
        nuevaCalificacion.setComentario(nuevoComentario);
        nuevaCalificacion.setCalificacion(Integer.parseInt(calificacion));
        EstacionamientoManager.agregarComentarioAEstacionamiento(nombreEstacionamiento, nuevaCalificacion);

        return new ModelAndView("redirect:adm");

    }

    @RequestMapping(value="/{name}/agregarCalificacionUsuario", method = RequestMethod.POST)
    public ModelAndView agregarCalificacionUsuario(HttpServletRequest request, ModelMap model) {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        String nuevoComentario = request.getParameter("comentario");
        String nombreEstacionamiento =  request.getParameter("originalName");
        String calificacion = request.getParameter("calificacion");
        if (calificacion==null || calificacion.equals("")) {
            calificacion = "0";
        }

        Calificacion nuevaCalificacion = new Calificacion();
        String usuario = obtenerUsuario();
        usuario = usuario.substring(0, usuario.lastIndexOf("@"));
        nuevaCalificacion.setUsuario(usuario);
        nuevaCalificacion.setComentario(nuevoComentario);
        nuevaCalificacion.setCalificacion(Integer.parseInt(calificacion));
        EstacionamientoManager.agregarComentarioAEstacionamiento(nombreEstacionamiento, nuevaCalificacion);

        enviarAvisoAAdministrador(nombreEstacionamiento, nuevaCalificacion);

        return new ModelAndView("redirect:/obl/ver/" + nombreEstacionamiento);
    }

    private void enviarAvisoAAdministrador(String nombreEstacionamiento, Calificacion calificacion) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        String msgBody = "Se recibió una nueva calificación sobre el estacionamiento \"" + nombreEstacionamiento +"\""
                + "\n\n" + "Calificación: " + calificacion.getCalificacion() + "\n"
                + "Comentario: " + calificacion.getComentario();
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new  InternetAddress("info@appenlanube-barbieri-gamboa.appspotmail.com", "Control de Estacionamiento"));
            String to = EstacionamientoManager.obtenerResponsableDeEstacionamiento(nombreEstacionamiento);
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to, to.substring(0, to.lastIndexOf("@"))));
            msg.setSubject("Nueva calificación para " + nombreEstacionamiento);
            msg.setText(msgBody);
            Transport.send(msg);

        } catch (Exception e) {
            System.out.println("Error enviando el mail de aviso sobre calificación: " + e.getMessage());
        }
    }

    private String obtenerUsuario() {
        MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
        Object memcacheValue = syncCache.get("usuario");
        String usuario = null;
        if (memcacheValue==null) {
            usuario = UserServiceFactory.getUserService().getCurrentUser().getEmail();
        } else {
            usuario = memcacheValue.toString();
        }

        return usuario;
    }

    @RequestMapping(value="/ver", method = RequestMethod.GET)
    public String irAEstacionamientoUsuario(ModelMap model) {
        String usuario = obtenerUsuario();

        /*Estacionamiento estacionamiento = EstacionamientoManager.obtenerEstacionamientoDeUsuario(usuario);
        model.addAttribute("estacionamiento", estacionamiento);
        model.addAttribute("logout", UserServiceFactory.getUserService().createLogoutURL("/obl/login"));*/
        List<String> lista = EstacionamientoManager.obtenerNombreDeTodos();
        model.addAttribute("lista", lista);
        model.addAttribute("logout", UserServiceFactory.getUserService().createLogoutURL("/"));
        model.addAttribute("seleccionado", null);

        return "listaestacionamiento";
    }

    @RequestMapping(value="/ver/{name}", method = RequestMethod.GET)
    public String estacionamientoUsuario(@PathVariable String name,
                                               HttpServletRequest request, ModelMap model) {

        Estacionamiento estacionamiento = EstacionamientoManager.obtenerEstacionamiento(name);
        List<String> lista = EstacionamientoManager.obtenerNombreDeTodos();
        model.addAttribute("lista", lista);
        model.addAttribute("seleccionado", estacionamiento);
        model.addAttribute("logout", UserServiceFactory.getUserService().createLogoutURL("/"));

        return "listaestacionamiento";
        //return new ModelAndView("redirect:../../obl/ver");
    }

    @RequestMapping(value="/adm", method = RequestMethod.GET)
    public String irAEstacionamientoAdm(ModelMap model) {
        String usuario = obtenerUsuario();

        Estacionamiento estacionamiento = EstacionamientoManager.obtenerEstacionamientoDeUsuario(usuario);
        model.addAttribute("estacionamiento", estacionamiento);
        model.addAttribute("logout", UserServiceFactory.getUserService().createLogoutURL("/"));

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
            MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
            syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
            syncCache.put("usuario", user.getEmail(), Expiration.byDeltaSeconds(Integer.MAX_VALUE));
            //obtener usuario de datastore
            //si es administrador, ir a /adm
            //si es usuario, ir a /usr
            if (UsuarioUtil.checkUserRole(user.getEmail(), Rol.ADMINISTRADOR)){
                //resp.getWriter().println("es administrador, <p><a href=\"" + userService.createLogoutURL(thisURL) + "\">Salir</a></p>");
                resp.sendRedirect("/obl/adm");
            } else {
                //resp.getWriter().println("es usuario comun, <p><a href=\"" + userService.createLogoutURL(thisURL) + "\">Salir</a></p>");
                resp.sendRedirect("/obl/ver");
            }
        } else {
            //enviar a login
            resp.sendRedirect(userService.createLoginURL(thisURL));
        }
    }

}
