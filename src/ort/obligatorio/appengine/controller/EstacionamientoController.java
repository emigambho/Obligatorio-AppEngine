package ort.obligatorio.appengine.controller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ort.obligatorio.appengine.estacionamiento.Estacionamiento;
import ort.obligatorio.appengine.estacionamiento.Rol;
import ort.obligatorio.appengine.manager.EstacionamientoManager;
import ort.obligatorio.appengine.util.UsuarioUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/obl")
public class EstacionamientoController {

    @RequestMapping(value="/adm", method = RequestMethod.GET)
    public String irAEstacionamientoAdm(ModelMap model) {
        Estacionamiento estacionamiento = EstacionamientoManager.obtenerEstacionamientoDefault();
        model.addAttribute("estacionamiento", estacionamiento);
        
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
