package cl.cokke.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.cokke.model.Tutorial;
import cl.cokke.services.TutorialService;

@RestController
@RequestMapping("/api/v1")
public class TutorialRestController {

	//inyeiccion de dependencias
	@Autowired
	private TutorialService tutorialService;
	
	//getAllTutorial
	@GetMapping("/tutoriales")
	//@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<List<Tutorial>> getAllTutorial(@RequestParam(required = false) String titulo){
		
		try {
			List<Tutorial> tutoriales = new ArrayList<Tutorial>();//se crea una lista para almacenar los tutoriales
			
			if (titulo == null) {//si el titulo como parametro de entrada esta vacio
				tutoriales = tutorialService.findAll();//se consulta por todos los tutoriales
			} else {// no
				tutoriales = tutorialService.findByTitulo(titulo);//se consulta por tutoriales por titulo
			}
			
			if (tutoriales.isEmpty()) {//si List<Tutirial> tutuariales esta vacia
				return new ResponseEntity<>(tutoriales, HttpStatus.NO_CONTENT);//se retorna un arreglo vacio, y status de no contenido 
			}
			return new ResponseEntity<>(tutoriales, HttpStatus.OK);//se retorna el arreglo de tutoriales y status ok
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);//si sucede un error de compilacion en el servidor, se retorna status internal_server_error
		}	
	}
	//getTutorialById
	@GetMapping("/tutoriales/{id}")
	public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") Long id){
		
		try {
			Optional<Tutorial> tutorialEncontrado = tutorialService.findById(id);//consulta de tutorial por id
			
			if (tutorialEncontrado.isPresent()) {//si tutorial esta presente, sus datos estan presentes
				return new ResponseEntity<>(tutorialEncontrado.get(), HttpStatus.OK);//se retorna el tutorialEncontrado y status ok
			} else {//no
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);//se retorna status not_found
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);//si sucede un error de compilacion en el servidor, se retorna status internal_server_error
		}	
	}
	//createTutorial
	@PostMapping("/tutoriales")
	public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial){
		
		try {
			if (tutorial.getDescripcion() != null && tutorial.getTitulo() != null) {//se valida si descripcion y titulo estan presentes
				Tutorial tutorialGuardado = tutorialService.save(tutorial);//se guarda el registro
				return new ResponseEntity<>(tutorialGuardado, HttpStatus.CREATED);//se retorna el nuevo registro y status created
			} else {//no
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);//se retorna que no es aceptado el request
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);//si sucede un error de compilacion en el servidor, se retorna status internal_server_error
		}	
	}
	//updateTutorial
	@PutMapping("/tutoriales/{id}")
	public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id")Long id, @RequestBody Tutorial tutorial){
		
		try {
			Optional<Tutorial> tutorialEncontrado = tutorialService.findById(id);
			if (tutorialEncontrado.isPresent()) {				
				tutorialEncontrado.get().setTitulo(tutorial.getTitulo());
				tutorialEncontrado.get().setDescripcion(tutorial.getDescripcion());
				tutorialEncontrado.get().setPublicado(tutorial.isPublicado());
				return new ResponseEntity<>(tutorialService.save(tutorialEncontrado.get()), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	//deleteTutorial
	@DeleteMapping("/tutoriales/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id")Long id){
		
		try {
			Optional<Tutorial> tutorial = tutorialService.findById(id);//se busca el tutorial por id
			if (tutorial.isPresent()) {//si el tutorial esta presente tutorialService.findById(id).isPresent()
				tutorialService.deleteById(id);//se elimina el registro del tutorial por id
				return new ResponseEntity<>(HttpStatus.OK);//se retorna status ok
			} else {//no
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);//se retorna status not_found
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	//deleteAllTutorial
	@DeleteMapping("/tutoriales")
	public ResponseEntity<HttpStatus> deleteAllTutorial(){
		
		try {
			tutorialService.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	//findByPublicado
	@GetMapping("/tutoriales/publicados")
	public ResponseEntity<List<Tutorial>> findByPublicado(){
		
		try {
			List<Tutorial> tutoriales = tutorialService.findByPublicado(true);//se consultan por todos los tutoriales publicados
			
			if (tutoriales.isEmpty()) {//se la lista esta vacia
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);//se retorna status no_content
			}else {
				return new ResponseEntity<>(tutoriales, HttpStatus.OK);//se retorna status ok
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
}
