package com.eva.blog.backend.controllers;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eva.blog.backend.model.services.IComentario;
import com.eva.blog.backend.model.services.IComercioService;
import com.eva.blog.backend.models.entity.Comercio;

@CrossOrigin(origins={"*"})
@RestController
@RequestMapping("/api")
public class ComercioRestController {
	@Autowired
	private IComercioService comercioService;
	@Autowired
	private IComentario comentarioService;

	private final Logger log = LoggerFactory.getLogger(ComercioRestController.class);
	
	@GetMapping("/comercios")
	public List<Comercio> index(){
		List<Comercio> comercios = comercioService.findAll();
		for (Comercio comercio: comercios) {
			comercio.setComentarios(comentarioService.ComentariosComercios(comercio.getId()));
		}
		return comercios;
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/comercios/page/{page}")
	public Page<Comercio> index(@PathVariable Integer page){
		Pageable pageable = PageRequest.of(page, 5);
		return comercioService.findAll(pageable);
		
	}
	
	@GetMapping("/comercios/{actividad}/busqueda")
	public  List<Comercio>  findByName(@PathVariable String actividad) {
		return comercioService.findByName(actividad);	
	}
	

	@GetMapping("/comercios/{mes}/mes")
	public  List<Comercio>  findByDate(@PathVariable Long mes) {
		return comercioService.findByDate(mes);	
	}
	
	@GetMapping("/comercios/{mes}/comercio")
	public  List<Comercio>  findOneComercioByDate(@PathVariable Long mes) {
		return comercioService.findOneComercioByDate(mes);	
	}
	
	@GetMapping("/comercios/date/comercio")
	public  List<Comercio>  findComercioByDate() {
		return comercioService.findComercioByDate();	
		
	}
	@GetMapping("/comercios/meses")
	public  int[] buscarMeses() {
		return comercioService.buscarMeses();	
		
	}
	@GetMapping("/comercios/maxLikes")
	public  List <Comercio> findMaxLikes() {
		return comercioService.findMaxLikes();	
	}
	
	@GetMapping("/comercios/maxVisitas")
	public  List <Comercio> findMaxVisitas() {
		return comercioService.findMaxVisitas();	
	}
	
	@GetMapping("/comercios/allActividad")
	public  String[] actividad() {
		return comercioService.actividad();	
	}
	@GetMapping("/comercios/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Comercio comercio= null;
		Map<String, Object> response = new HashMap<>();
		try {
			 comercio=comercioService.findById(id);	
			 comercio.setComentarios(comentarioService.ComentariosComercios(comercio.getId()));
		}
		catch(DataAccessException e) {
		
					response.put("mensaje", "Error al realizar la consulta en la base de datos");
					response.put("error",  e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		if(comercio==null) {	
					response.put("mensaje",  "el comercio con ID".concat(" : ").concat(id.toString().concat(" no existe en la base de datos")));
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(comercio, HttpStatus.OK);
	}
	
	@PostMapping("/comercios/{id}/likes")
	public ResponseEntity<?> addLike(@PathVariable Long id) {
		
		Comercio comercio = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			comercio = comercioService.addLike(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el update de likes en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("comercio", comercio);
		return new ResponseEntity<Comercio>(comercio, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/comercios/crear")
	public ResponseEntity<?> create(@Valid @RequestBody Comercio comercio, BindingResult result) {
		
		Comercio comercioNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> error = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("error", error);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			comercioNew = comercioService.save(comercio);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El comercio ha sido creado con éxito!");
		response.put("comercio", comercioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	

	@PostMapping("/comercios/{id}/visitas")
	public ResponseEntity<?> addVisitas(@PathVariable Long id) {
		
		Comercio comercio = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			comercio = comercioService.addVisitas(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el update de visitas en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("comercio", comercio);
		return new ResponseEntity<Comercio>(comercio, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/comercios/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Comercio comercio, BindingResult result, @PathVariable Long id) {

		Comercio comercioActual = comercioService.findById(id);

		Comercio comercioUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (comercioActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el comercio ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			comercioActual.setId(comercio.getId());
			comercioActual.setNombre(comercio.getNombre());
			comercioActual.setLikes(comercio.getLikes());

			comercioUpdated = comercioService.save(comercioActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el comercio en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El comercio ha sido actualizado con éxito!");
		response.put("cliente", comercioUpdated);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/comercios/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("archivo1") 
	MultipartFile archivo1, @RequestParam("archivo2") MultipartFile archivo2, @RequestParam("id") Long id){

		Map<String, Object> response = new HashMap<>();
		
		Comercio comercio = comercioService.findById(id);
		
		if(!archivo.isEmpty()&&!archivo1.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString() + "_" +  archivo.getOriginalFilename().replace(" ", "");
			String nombreArchivo1 = UUID.randomUUID().toString() + "_" +  archivo1.getOriginalFilename().replace(" ", "");
			String nombreArchivo2 = UUID.randomUUID().toString() + "_" +  archivo2.getOriginalFilename().replace(" ", "");

	
			Path rutaArchivo = Paths.get("descargas").resolve(nombreArchivo).toAbsolutePath();
			Path rutaArchivo1= Paths.get("descargas").resolve(nombreArchivo1).toAbsolutePath();
			Path rutaArchivo2= Paths.get("descargas").resolve(nombreArchivo2).toAbsolutePath();
		

			log.info(rutaArchivo.toString());
			log.info(rutaArchivo1.toString());
			log.info(rutaArchivo2.toString());

			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
				Files.copy(archivo1.getInputStream(), rutaArchivo1);
				Files.copy(archivo2.getInputStream(), rutaArchivo2);

			

			} catch (IOException e) {
				response.put("mensaje", "Error al subir las imágenes del comercio " + nombreArchivo + nombreArchivo1 + nombreArchivo2 );
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			String nombreFotoAnterior = comercio.getImg();
			String nombreFotoAnterior1 = comercio.getImg1();
			String nombreFotoAnterior2 = comercio.getImg2();


			if(nombreFotoAnterior !=null && nombreFotoAnterior.length() >0||nombreFotoAnterior1 !=null && nombreFotoAnterior1.length() >0||nombreFotoAnterior2 !=null && nombreFotoAnterior2.length() >0) {
				Path rutaFotoAnterior = Paths.get("descargas").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
				Path rutaFotoAnterior1 = Paths.get("descargas").resolve(nombreFotoAnterior1).toAbsolutePath();
				File archivoFotoAnterior1 = rutaFotoAnterior1.toFile();
				Path rutaFotoAnterior2 = Paths.get("descargas").resolve(nombreFotoAnterior2).toAbsolutePath();
				File archivoFotoAnterior2 = rutaFotoAnterior2.toFile();
				if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			
				if(archivoFotoAnterior1.exists() && archivoFotoAnterior1.canRead()) {
					archivoFotoAnterior1.delete();
				}
				if(archivoFotoAnterior2.exists() && archivoFotoAnterior2.canRead()) {
					archivoFotoAnterior2.delete();		
				}
			}
			
			comercio.setImg(nombreArchivo);
			comercio.setImg1(nombreArchivo1);
			comercio.setImg2(nombreArchivo2);

			comercioService.save(comercio);
			
			response.put("comercio", comercio);
			response.put("mensaje", "Has subido correctamente las imagénes: " + nombreArchivo + nombreArchivo1 + nombreArchivo2);
			
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	@Secured("ROLE_ADMIN")
	@PostMapping("/comercios/uploadOneFoto/{imgUpdated}")
	public ResponseEntity<?> uploadOneFoto(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id, @PathVariable Long imgUpdated){

		Map<String, Object> response = new HashMap<>();
		
		Comercio comercio = comercioService.findById(id);
		String nombreFotoAnterior  ="";
		
		if(!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString() + "_" +  archivo.getOriginalFilename().replace(" ", "");
			Path rutaArchivo = Paths.get("descargas").resolve(nombreArchivo).toAbsolutePath();
			log.info(rutaArchivo.toString());
			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir las imágenes del comercio " + nombreArchivo );
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			if(imgUpdated==1) {
				nombreFotoAnterior = comercio.getImg();

				comercio.setImg(nombreArchivo);
			}
			else if(imgUpdated==2) {
			 nombreFotoAnterior = comercio.getImg1();
				comercio.setImg1(nombreArchivo);
			}
			else {
				 nombreFotoAnterior = comercio.getImg2();
					comercio.setImg2(nombreArchivo);
			}
			
			if(nombreFotoAnterior !=null && nombreFotoAnterior.length() >0) {
				Path rutaFotoAnterior = Paths.get("descargas").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();

				if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			}
			comercioService.save(comercio);
			response.put("comercio", comercio);
			response.put("mensaje", "Has subido correctamente las imágenes: " + nombreArchivo +"a la imegen"+imgUpdated );
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/descargas/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		
		Path rutaArchivo = Paths.get("descargas").resolve(nombreFoto).toAbsolutePath();
		log.info(rutaArchivo.toString());
		
		Resource recurso = null;
		
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		if(!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error no se pudo cargar la imagen: " + nombreFoto);
		}
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
		
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/comercio/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			comercioService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el comercio de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El comercio ha sido  eliminado con éxito!");	
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}

	