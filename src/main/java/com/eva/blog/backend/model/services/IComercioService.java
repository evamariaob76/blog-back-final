package com.eva.blog.backend.model.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.eva.blog.backend.models.entity.Comercio;

public interface IComercioService {

	public List<Comercio> findAll();
	
	public Page<Comercio> findAll(Pageable pageable);
	
	public Comercio save(Comercio comercio);

	public Comercio findById(Long id);

	public void delete(Long id);

	public Comercio addLike(Long id);
	
	public  List<Comercio> findByName(String actividad);
	
	public Comercio addVisitas (Long id);
	
	public  List<Comercio> findByDate(Long mes);

	public  int[] buscarMeses();

	public  List<Comercio> findOneComercioByDate(Long mes);

	public  List<Comercio> findComercioByDate();
	
	public  List<Comercio> findMaxLikes();
	
	public  List<Comercio> findMaxVisitas();

	public  String[]  actividad();


}