package com.eva.blog.backend.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eva.blog.backend.models.entity.Cliente;
import com.eva.blog.backend.models.entity.Comentario;

public interface IClienteDao extends JpaRepository<Cliente, Long>{
	 @Query(value=" SELECT email FROM clientes",nativeQuery = true)
	    List<String> getEmail();

}
