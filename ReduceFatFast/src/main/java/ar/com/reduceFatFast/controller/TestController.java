/**
 * 
 */
package ar.com.reduceFatFast.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.reduceFatFast.model.Comida;
import ar.com.reduceFatFast.model.Dia;
import ar.com.reduceFatFast.model.DietaSemanal;
import ar.com.reduceFatFast.model.Ingrediente;
import ar.com.reduceFatFast.service.TestService;

/**
 * @author joaco
 *
 */
@RestController
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private TestService service;
	
	@RequestMapping("/crearComida")
	public Comida crearComida(){
		Comida comida = new Comida("Pizza Explosiva");
		comida.agregarIngrediente("Queso", 500, "gr");
		comida.agregarIngrediente("Papas Fritas", 100, "gr");
		comida.agregarIngrediente("Huevos", 2, "huevos fritos");
		comida.agregarIngrediente("Fiambre", 100, "gr");
		this.getService().add(comida);
		return comida;
	}
	
	@RequestMapping("/getComidas")
	public List<Comida> getComidas(){
		List<Comida> comidas = this.getService().getAllComidas();
		for (Comida comida : comidas) {
			logger.info("Comida: " + comida.getNombre());
			Hibernate.initialize(comida.getIngredientes());
			for (Ingrediente ingrediente : comida.getIngredientes()) {
				Hibernate.initialize(ingrediente);
				logger.info("Ingredientes: " + ingrediente.getName());
			}
		}
		return comidas;
	}
	
	@RequestMapping("/crearDia")
	public Dia crearDia(){
		Dia dia = new Dia();
		List<Comida> comidas = this.getComidas();
		dia.getComidas().add(comidas.get(0));
		dia.getComidas().add(comidas.get(0));
		dia.getComidas().add(comidas.get(0));
		dia.getComidas().add(comidas.get(0));
		
		this.getService().add(dia);
		
		return dia;
	}
	
	@RequestMapping("/getDias")
	public List<Dia> getAllDias(){
		return (List<Dia>) this.getService().getAllDias();
	}
	
	@RequestMapping("/addDieta")
	public DietaSemanal addDieta(){
		DietaSemanal dieta = new DietaSemanal();
		List<Comida> comidas = this.getComidas();
		List<Dia> dias = new ArrayList<Dia>();
		Dia dia;
		for (int i=0; i<7; i++) {
			dia = new Dia();
			dia.setCantidadComidasPorDia(4);
			for (int j=0; j<4; j++) {	
				dia.getComidas().add(comidas.get(0));
			}
			dias.add(dia);
		}
		dieta.agregarDias(dias);
		this.getService().add(dieta);
		return dieta;
	}
	
	@RequestMapping("/getDietas")
	public List<DietaSemanal> getDietas(){
		return this.getService().getDietas();
	}

	/**
	 * @return the service
	 */
	public TestService getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(TestService service) {
		this.service = service;
	}

}