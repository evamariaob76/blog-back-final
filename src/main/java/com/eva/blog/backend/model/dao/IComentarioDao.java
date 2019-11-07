package com.eva.blog.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.eva.blog.backend.models.entity.Comentario;

public interface IComentarioDao extends CrudRepository <Comentario, Long> {
	 @Query(value=" SELECT * FROM comentarios c JOIN comercios p ON(id_comercio=p.id) AND p.id=:id_comercio ORDER BY c.id DESC",nativeQuery = true)
	    List<Comentario> ComentariosComercios(@Param("id_comercio")Long id_comercio);
	 
	 @Query(value=" SELECT COUNT(comentario)  FROM comentarios WHERE id_comercio=:id_comercio",nativeQuery = true)
	 int NumeroComentarios(@Param("id_comercio")Long id_comercio);
	 

}



