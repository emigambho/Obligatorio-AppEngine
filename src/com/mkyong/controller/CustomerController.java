package com.mkyong.controller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.FilterOperator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ort.obligatorio.appengine.estacionamiento.Estacionamiento;
import ort.obligatorio.appengine.estacionamiento.Parcela;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	//cambio en customer controller

	@RequestMapping(value="/addCustomerPage", method = RequestMethod.GET)
	public String getAddCustomerPage(ModelMap model) {

		return "add";

	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest request, ModelMap model) {

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
	    Key customerKey = KeyFactory.createKey("Customer", name);
	        
		Date date = new Date();
        Entity customer = new Entity("Customer", customerKey);
        customer.setProperty("name", name);
        customer.setProperty("email", email);
        customer.setProperty("date", date);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(customer);
        
        return new ModelAndView("redirect:list");
        
	}
		
	@RequestMapping(value="/update/{name}", method = RequestMethod.GET)
	public String getUpdateCustomerPage(@PathVariable String name, 
			HttpServletRequest request, ModelMap model) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Customer");
		query.addFilter("name", FilterOperator.EQUAL, name);
		PreparedQuery pq = datastore.prepare(query);
		
		Entity e = pq.asSingleEntity();
		model.addAttribute("customer",  e);
		
		return "update";

	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, ModelMap model) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		 
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String originalName =  request.getParameter("originalName");
		
		Query query = new Query("Customer");
		query.addFilter("name", FilterOperator.EQUAL, originalName);
		PreparedQuery pq = datastore.prepare(query);
		Entity customer = pq.asSingleEntity();
		
		customer.setProperty("name", name);
		customer.setProperty("email", email);
		customer.setProperty("date", new Date());

        datastore.put(customer);
        
        //return to list
        return new ModelAndView("redirect:list");
        
	}
		
	@RequestMapping(value="/delete/{name}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable String name,
			HttpServletRequest request, ModelMap model) {

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        
        Query query = new Query("Customer");
		query.addFilter("name", FilterOperator.EQUAL, name);
		PreparedQuery pq = datastore.prepare(query);
		Entity customer = pq.asSingleEntity();
		
        datastore.delete(customer.getKey());
        
        //return to list
        return new ModelAndView("redirect:../list");
        
	}
	
	
	//get all customers
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String listCustomer(ModelMap model) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("Customer").addSort("date", Query.SortDirection.DESCENDING);
	    List<Entity> customers = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(10));
	    
	    model.addAttribute("customerList",  customers);
	    
		return "listrenombrado";

	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public @ResponseBody Estacionamiento listar() {

		Estacionamiento	e = new Estacionamiento();
		e.setNombre("nombre del estacionamiento");
		e.setPuntaje(5);
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

		Entity entityEstacionamiento = new Entity("Estacionamiento", e.getNombre());
		entityEstacionamiento.setProperty("nombre", e.getNombre());
		entityEstacionamiento.setProperty("puntaje", e.getPuntaje());
		/*List<String> strings = new ArrayList<String>();
		for (Parcela p : parcelas) {
			strings.add(p.toString());
		}
		entityEstacionamiento.setProperty("parcelas", strings);*/
		entityEstacionamiento.setProperty("parcelas", e.getParcelas());

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(entityEstacionamiento);



		datastore = DatastoreServiceFactory.getDatastoreService();

		Query query = new Query("Estacionamiento");
		query.addFilter("nombre", FilterOperator.EQUAL, "nombre del estacionamiento");
		PreparedQuery pq = datastore.prepare(query);
		Entity estacionamientoEnDataStore = pq.asSingleEntity();
		Estacionamiento e2 = new Estacionamiento();
		e2.setPuntaje(Double.valueOf(estacionamientoEnDataStore.getProperty("puntaje").toString()).doubleValue());
		e2.setNombre(estacionamientoEnDataStore.getProperty("nombre").toString());
		//List<String> stringsdedatastore = (List<String>)estacionamientoEnDataStore.getProperty("parcelas");
		e2.setParcelas((List<Parcela>)estacionamientoEnDataStore.getProperty("parcelas"));

		return e2;
	}

}