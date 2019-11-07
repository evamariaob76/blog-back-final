package com.eva.blog.backend.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "comentarios")
public class Comentario implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100000)	
	private String comentario;
	
	@Column(length = 100000)	
	private String contestacion;

	private Long id_comercio;
	
	private String nombre;
	
	private String email;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	public Long getId_comercio() {
		return id_comercio;
	}

	public void setId_comercio(Long id_comercio) {
		this.id_comercio = id_comercio;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getContestacion() {
		return contestacion;
	}

	public void setContestacion(String contestacion) {
		this.contestacion = contestacion;
	}

	private static final long serialVersionUID = 1L;

}
