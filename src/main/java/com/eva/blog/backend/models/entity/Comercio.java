package com.eva.blog.backend.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "comercios")
public class Comercio implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comentario> comentarios;
	

	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	

	@Column
	private Long likes;

	
	@Column
	private String nombre;
	
	
	@Column(length = 100000)	
	private String  resumen;
	
	@Column
	private String aparicion;
	
	@Column
	private Double lat;
	
	@Column
	private Double lng;
	
	@Column
	private String direccion;
	
	@Column
	private String cp;
	
	@Column
	private String telefono;
	
	@Column
	private String facebook;
	
	@Column(length = 100000, name="actividad")	
	private String actividad;
	
	@Column
	private String img;
	@Column
	private String img1;
	@Column
	private String img2;
	
	@Column
	private String parrafoOne;
	
	@Column
	private String parrafoTwo;
	private Long visitas;

	public List<Comentario> getComentarios() {
		return comentarios;
	}



	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public Date getCreateAt() {
		return createAt;
	}




	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}




	public Long getLikes() {
		return likes;
	}




	public void setLikes(Long likes) {
		this.likes = likes;
	}




	public String getNombre() {
		return nombre;
	}




	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




	public String getResumen() {
		return resumen;
	}




	public void setResumen(String resumen) {
		this.resumen = resumen;
	}




	public String getAparicion() {
		return aparicion;
	}




	public void setAparicion(String aparicion) {
		this.aparicion = aparicion;
	}




	public Double getLat() {
		return lat;
	}




	public void setLat(Double lat) {
		this.lat = lat;
	}




	public Double getLng() {
		return lng;
	}




	public void setLng(Double lng) {
		this.lng = lng;
	}




	public String getDireccion() {
		return direccion;
	}




	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}




	public String getCp() {
		return cp;
	}




	public void setCp(String cp) {
		this.cp = cp;
	}




	public String getTelefono() {
		return telefono;
	}




	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}




	public String getFacebook() {
		return facebook;
	}




	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}




	public String getActividad() {
		return actividad;
	}




	public void setActividad(String actividad) {
		this.actividad = actividad;
	}




	public String getImg() {
		return img;
	}




	public void setImg(String img) {
		this.img = img;
	}




	public String getImg1() {
		return img1;
	}




	public void setImg1(String img1) {
		this.img1 = img1;
	}




	public String getImg2() {
		return img2;
	}



	public void setImg2(String img2) {
		this.img2 = img2;
	}



	public Long getVisitas() {
		return visitas;
	}



	public void setVisitas(Long visitas) {
		this.visitas = visitas;
	}



	public String getParrafoOne() {
		return parrafoOne;
	}



	public void setParrafoOne(String parrafoOne) {
		this.parrafoOne = parrafoOne;
	}



	public String getParrafoTwo() {
		return parrafoTwo;
	}



	public void setParrafoTwo(String parrafoTwo) {
		this.parrafoTwo = parrafoTwo;
	}


	private static final long serialVersionUID = 1L;


}
