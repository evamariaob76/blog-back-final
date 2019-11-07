package com.eva.blog.backend.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.eva.blog.backend.models.entity.Comercio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface IComercioDao extends JpaRepository<Comercio, Long> {
	 @Query(value="SELECT * FROM comercios WHERE actividad LIKE %:actividad%",nativeQuery = true)
	    List<Comercio> findByName(@Param("actividad")String actividad);
	 
	 
	 @Query(value="SELECT  * from  comercios  WHERE  date_format(create_at, '%m')=:mes",nativeQuery = true)
	    List<Comercio> findByDate(@Param("mes")Long mes);

	 @Query(value="SELECT DISTINCT MONTH (create_at) FROM comercios",nativeQuery = true)
	    int[] buscarMeses();
	 
	 @Query(value="SELECT * , MAX(create_at) AS latest_date FROM comercios WHERE MONTH(create_at)=:mes",nativeQuery = true)
	    List<Comercio> findOneComercioByDate(@Param("mes")Long mes);
	 
	 @Query(value="SELECT *, MAX(create_at) as fecha FROM comercios GROUP BY MONTH(create_at)",nativeQuery = true)
	    List<Comercio> findComercioByDate();
	 
	 @Query(value="SELECT * FROM comercios  WHERE likes>0 order by likes DESC LIMIT 10",nativeQuery = true)
	    List<Comercio> findMaxLikes();
	 
	 @Query(value="SELECT * FROM comercios  WHERE visitas>0 order by visitas DESC LIMIT 10",nativeQuery = true)
	    List<Comercio> findMaxVisitas();
	 
	 @Query(value="SELECT DISTINCT actividad FROM comercios order by create_at DESC",nativeQuery = true)
	 String[] actividad();
}

