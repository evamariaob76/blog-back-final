package com.eva.blog.backend.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eva.blog.backend.model.dao.IComentarioDao;
import com.eva.blog.backend.models.entity.Comentario;

@Service
public class ComentarioImp implements IComentario {
	@Autowired
	private IComentarioDao comentarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Comentario> findAll(){
		return (List<Comentario>) comentarioDao.findAll();
	}

	@Override
	@Transactional
	public Comentario save(Comentario comentario) {
		return comentarioDao.save(comentario);
	}


	@Override
	@Transactional
	public void delete(Long id) {
		comentarioDao.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Comentario findById(Long id) {
		return comentarioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Comentario>ComentariosComercios(Long id_comercio){
		return comentarioDao.ComentariosComercios(id_comercio);	
	}

	@Override
	public int NumeroComentarios(Long id_comercio) {
		return comentarioDao.NumeroComentarios(id_comercio);

	}

}
