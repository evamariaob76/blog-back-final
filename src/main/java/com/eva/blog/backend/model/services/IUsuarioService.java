package com.eva.blog.backend.model.services;

import com.eva.blog.backend.models.entity.Cliente;
import com.eva.blog.backend.models.entity.Usuario;

public interface IUsuarioService {
	
	public Usuario findByUsername(String username);
	
	public Usuario findById(Long id);
	
	public Usuario save(Usuario usuario);

}
