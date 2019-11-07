package com.eva.blog.backend.model.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.eva.blog.backend.model.dao.IUsuarioDao;

import com.eva.blog.backend.model.services.IUsuarioService;
import com.eva.blog.backend.models.entity.Cliente;
import com.eva.blog.backend.models.entity.Comercio;
import com.eva.blog.backend.models.entity.Usuario;


@Service
public class UsuarioService implements  UserDetailsService, IUsuarioService {
	
	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null) {
			logger.error("Error en el login: no existe el usuario '"+username+"' en el sistema!");
			throw new UsernameNotFoundException("Error en el login: no existe el usuario '"+username+"' en el sistema!");
		}
		
		List<GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> logger.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

		@Override
		@Transactional(readOnly=true)
		public Usuario findByUsername(String username) {
			return usuarioDao.findByUsername(username);
			
		}

		@Override
		public Usuario findById(Long id) {
			return usuarioDao.findById(id).orElse(null);

		}

		@Override
		public Usuario save(Usuario usuario) {
			return usuarioDao.save(usuario);

		}
		

		
		
		}
	


