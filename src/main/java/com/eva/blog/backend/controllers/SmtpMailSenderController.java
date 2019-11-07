package com.eva.blog.backend.controllers;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eva.blog.backend.model.services.IClientesService;
import com.eva.blog.backend.model.services.IComercioService;
import com.eva.blog.backend.model.services.SmtpMailSender;
import com.eva.blog.backend.model.services.UsuarioService;
import com.eva.blog.backend.models.entity.Comercio;
import com.eva.blog.backend.models.entity.Usuario;

@RestController
public class SmtpMailSenderController {
	
	@Autowired
	private SmtpMailSender  smtpMailSender;
	
	@Autowired
	private IClientesService clienteService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private IComercioService comercioService;

	@PostMapping("/api/sendmail")
		public void sendMail(@RequestParam("id") Long id) throws MessagingException{
		Comercio comercio= null;
		List<String> emails = clienteService.getEmail();
		comercio= comercioService.findById(id)	;		
		for (String email : emails) {
	smtpMailSender.send(email,"Actualización de d'XIX al XXI", "Se ha actuallizado un nuevo comercio, con el nombre:  "+comercio.getNombre());
		}
	}
	
	@PostMapping("/api/sendmail/admin")
	public void sendMailAdmin(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido, @RequestParam("email") String email, @RequestParam("texto") String texto ) throws MessagingException{
		Long id=(long) 2;
		Usuario usuario= usuarioService.findById(id);
		
	smtpMailSender.send(usuario.getEmail(), nombre+" "+apellido, texto+" <br>mi email es: "+email);	
		}

	@PostMapping("/api/send/password")
	public void sendPassword(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,@RequestParam("username") String username,  @RequestParam("email") String email) throws MessagingException{
		Long id=(long) 2;
		Usuario usuario= usuarioService.findById(id);
		String enlace="http://localhost:4200/resetPasword";
		if(email.contentEquals(usuario.getEmail())&& username.contentEquals(usuario.getUsername())&&nombre.contentEquals(usuario.getNombre())&& apellido.contentEquals(usuario.getApellido())) {
		smtpMailSender.send(usuario.getEmail(), nombre+" "+apellido,usuario.getUsername()+" <br>el enlace para restablecer la contraseña es: "+enlace);	
		System.out.println(usuario+"ver"+usuario.getUsername());
		}
		else
		System.out.println(email+"  /mL/  "+usuario.getEmail());

	}
	}

	

