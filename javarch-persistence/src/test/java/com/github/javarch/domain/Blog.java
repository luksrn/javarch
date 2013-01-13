package com.github.javarch.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.github.javarch.persistence.orm.hibernate.AbstractPersistable;

@Entity
public class Blog extends AbstractPersistable {

	@NotEmpty
	private String titulo;
	
	@Transient
	private List<Post> postagens;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private User dono;

	public Blog() {
		super();
	}
	
	
	public Blog(String titulo) {
		super();
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Post> getPostagens() {
		return postagens;
	}

	public void setPostagens(List<Post> postagens) {
		this.postagens = postagens;
	}


	public User getDono() {
		return dono;
	}


	public void setDono(User dono) {
		this.dono = dono;
	}
	
	
}
