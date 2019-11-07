package com.eva.blog.backend.model.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.eva.blog.backend.models.entity.Comentario;

public interface IComentario {

	public List<Comentario> findAll();
	

	public Comentario save(Comentario comentario);
	
	public Comentario findById(Long id);
	
	public void delete(Long id);
	
	public List<Comentario>ComentariosComercios(Long id_comercio);
	
	public int NumeroComentarios(Long id_comercio);
	

}
