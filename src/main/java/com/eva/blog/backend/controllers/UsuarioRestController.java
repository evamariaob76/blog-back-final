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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eva.blog.backend.model.services.IUsuarioService;
import com.eva.blog.backend.models.entity.Comercio;
import com.eva.blog.backend.models.entity.Usuario;

@CrossOrigin(origins={"*"})
@RestController
@RequestMapping("/api")
public class UsuarioRestController {
	@Autowired
	private IUsuarioService usuarioService;
	
	private final Logger log = LoggerFactory.getLogger(UsuarioRestController.class);
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Usuario usuario= null;
		Map<String, Object> response = new HashMap<>();

		try {
			usuario=usuarioService.findById(id);	
		}
		catch(DataAccessException e) {
		
					response.put("mensaje", "Error al realizar la consulta en la base de datos");
					response.put("error",  e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		if(usuario==null) {	
					response.put("mensaje",  "el comercio con ID".concat(" : ").concat(id.toString().concat(" no existe en la base de datos")));
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(usuario, HttpStatus.OK);
	}
	
	@GetMapping("/descargasAdmin/img/{nombreFoto}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		
		Path rutaArchivo = Paths.get("descargasAdmin").resolve(nombreFoto).toAbsolutePath();
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
	@GetMapping("/fotoPortada/img/{fotoPortada}")
	public ResponseEntity<Resource> verfotoPortada(@PathVariable String fotoPortada){
		
		Path rutaArchivo = Paths.get("descargasAdmin").resolve(fotoPortada).toAbsolutePath();
		log.info(rutaArchivo.toString());
		
		Resource recurso = null;
		
		try {
			recurso = new UrlResource(rutaArchivo.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		if(!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error no se pudo cargar la imagen: " + fotoPortada);
		}
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
		
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}
	@Secured("ROLE_ADMIN")
	@PostMapping("/usuarios/foto")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo,  @RequestParam("id") Long id){

		Map<String, Object> response = new HashMap<>();
		
		Usuario usuario = usuarioService.findById(id);
		
		if(!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString() + "_" +  archivo.getOriginalFilename().replace(" ", "");
				
			Path rutaArchivo = Paths.get("descargasAdmin").resolve(nombreArchivo).toAbsolutePath();
			
			log.info(rutaArchivo.toString());
			

			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
				
			

			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen " + nombreArchivo);
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			String nombreFotoAnterior = usuario.getImg();



			if(nombreFotoAnterior !=null && nombreFotoAnterior.length() >0) {
				Path rutaFotoAnterior = Paths.get("descargasAdmin").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
			
				if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			
			
			}
			
			usuario.setImg(nombreArchivo);
			

			usuarioService.save(usuario);
			
			response.put("usuario", usuario);
			response.put("mensaje", "Has subido correctamente la imágen: " + nombreArchivo);
			
		}

		else {}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	@Secured("ROLE_ADMIN")
	@PostMapping("/usuarios/editarfotoPortada")
	public ResponseEntity<?> uploadverfotoPortada(@RequestParam("archivo") MultipartFile archivo,  @RequestParam("id") Long id){

		Map<String, Object> response = new HashMap<>();
		
		Usuario usuario = usuarioService.findById(id);
		
		if(!archivo.isEmpty()) {
			String nombreArchivo = UUID.randomUUID().toString() + "_" +  archivo.getOriginalFilename().replace(" ", "");
				
			Path rutaArchivo = Paths.get("descargasAdmin").resolve(nombreArchivo).toAbsolutePath();
			
			log.info(rutaArchivo.toString());
			

			try {
				Files.copy(archivo.getInputStream(), rutaArchivo);
				
			

			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen " + nombreArchivo);
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			String nombreFotoAnterior = usuario.getFotoPortada();



			if(nombreFotoAnterior !=null && nombreFotoAnterior.length() >0) {
				Path rutaFotoAnterior = Paths.get("descargasAdmin").resolve(nombreFotoAnterior).toAbsolutePath();
				File archivoFotoAnterior = rutaFotoAnterior.toFile();
			
				if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
					archivoFotoAnterior.delete();
				}
			
			
			}
			
			usuario.setFotoPortada(nombreArchivo);
			

			usuarioService.save(usuario);
			
			response.put("usuario", usuario);
			response.put("mensaje", "Has subido correctamente la imágen: " + nombreArchivo);
			
		}

		else {}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@PutMapping("/usuarios/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {

		Usuario usuarioActual = usuarioService.findById(id);

		Usuario usuarioUpdated = null;

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (usuarioActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el usuario ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			usuarioActual.setBio(usuario.getBio());
			usuarioActual.setDescripcion(usuario.getDescripcion());
			usuarioActual.setApellido(usuario.getApellido());
			usuarioActual.setNombre(usuario.getNombre());
			usuarioActual.setEmail(usuario.getEmail());
			usuarioUpdated = usuarioService.save(usuario);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario admin ha sido actualizado con éxito!");
		response.put("usuario", usuarioUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	@PutMapping("/usuarios/password")
	public ResponseEntity<?> updatePassword(@Valid @RequestBody String password,  BindingResult result) {

		Usuario usuarioActual = usuarioService.findById((long) 2);
	

		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (usuarioActual == null) {
			response.put("mensaje", "Error: no se pudo editar");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			usuarioActual.setPassword(passwordEncoder.encode(password));
			System.out.println("es"+usuarioActual.getApellido());
			System.out.println("pass"+usuarioActual.getPassword());
			System.out.println(password);
			usuarioService.save(usuarioActual);


		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la contraseña en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario admin ha sido actualizado con éxito!");
		response.put("usuario", usuarioActual);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
}
