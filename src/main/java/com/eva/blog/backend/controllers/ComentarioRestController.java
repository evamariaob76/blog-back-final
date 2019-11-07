package com.eva.blog.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eva.blog.backend.model.services.IComentario;
import com.eva.blog.backend.models.entity.Cliente;
import com.eva.blog.backend.models.entity.Comentario;
import com.eva.blog.backend.models.entity.Comercio;

@CrossOrigin(origins={"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ComentarioRestController {
	@Autowired
	private IComentario comentarioService;
	
	@GetMapping("/comentarios")
	public List<Comentario> index(){
		return comentarioService.findAll();
		
	}
	
	@GetMapping("/comentarios/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Comentario comentario= null;
		Map<String, Object> response = new HashMap<>();
	
		try {
			comentario=comentarioService.findById(id);	
		}
		catch(DataAccessException e) {
		
					response.put("mensaje", "Error al realizar la consulta en la base de datos");
					response.put("error",  e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
					
		if(comentario==null) {	
		
						response.put("mensaje",  "el cliente con ID".concat(" : ").concat(id.toString().concat(" no existe en la base de datos")));
						return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

				}
					
		return new ResponseEntity(comentario, HttpStatus.OK);
	}
	@GetMapping("/comentarios/comercio/{id_comercio}")
	public ResponseEntity<?> ComentariosComercios(@PathVariable Long id_comercio) {
		List<Comentario> comentario= null;
		Map<String, Object> response = new HashMap<>();
	
		try {
			comentario=comentarioService.ComentariosComercios(id_comercio);
		}
		catch(DataAccessException e) {
		
					response.put("mensaje", "Error al realizar la consulta en la base de datos");
					response.put("error",  e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
					
		if(comentario==null) {	
		
						response.put("mensaje",  "el cliente con ID".concat(" : ").concat(id_comercio.toString().concat(" no existe en la base de datos")));
						return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

				}
					
		return new ResponseEntity(comentario, HttpStatus.OK);
	}
	
	@GetMapping("/comentarios/numero/{id_comercio}")
	public int NumeroComentarios(@PathVariable Long id_comercio) {
		int numeroComentarios= (int) 0;
		
			numeroComentarios=comentarioService.NumeroComentarios(id_comercio);

					
		return numeroComentarios;
	}

	/*@GetMapping("/comentarios/numero/{id_comercio}")
	public int numeroComentarios(@PathVariable Long id_comercio) {
		List<Comentario> comentario= null;
		Map<String, Object> response = new HashMap<>();
	
		try {
			comentario=comentarioService.ComentariosComercios(id_comercio);

		}
		catch(DataAccessException e) {
		
					response.put("mensaje", "Error al realizar la consulta en la base de datos");
					response.put("error",  e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
		}
					
		if(comentario==null) {	
		
						response.put("mensaje",  "el cliente con ID".concat(" : ").concat(id_comercio.toString().concat(" no existe en la base de datos")));

				}
					
		return comentario.size();
	}*/
	@PostMapping("/comentarios/{id}")
	public ResponseEntity<?> create(@Valid @RequestBody Comentario comentario, @PathVariable Long id, BindingResult result) {
		
		Comentario comentarioNew = null;
		Map<String, Object> response = new HashMap<>();
		
		/*if(result.hasErrors()) {

			List<String> error = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("error", error);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}*/
		
		try {
			comentario.setId_comercio(id);
			comentarioNew = comentarioService.save(comentario);
			System.out.print(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El comentario ha sido creado con éxito!");
		response.put("comentario", comentarioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		//return new Re

}
	@Secured("ROLE_ADMIN")
	@PutMapping("/comentarios/contestacion/{id_comercio}/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Comentario comentario, BindingResult result, @PathVariable Long id, @PathVariable Long id_comercio) {

		Comentario comentarioActual = comentarioService.findById(id);

		Comentario comentarioUpdated= null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (comentarioActual == null) {
			response.put("mensaje", "Error: no se pudo editar el comentario "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			//comentarioUpdated = comentarioService.save(comentarioActual);
			comentarioActual.setContestacion(comentario.getContestacion());
			comentarioUpdated = comentarioService.save(comentarioActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el comercio en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El comercio ha sido actualizado con éxito!");
		response.put("comercio", comentarioUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/comentarios/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			comentarioService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el comentario de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El comentario ha sido eliminado con éxito!");	
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	

}
