package com.github.javarch.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.javarch.persistence.annotation.DateCreated;
import com.github.javarch.persistence.orm.hibernate.AbstractPersistable;

@Entity
@AttributeOverride( name="id", column = @Column(name="idBlog") )
public class Blog extends AbstractPersistable {

	@NotEmpty
	private String titulo;
	
	@Transient
	private List<Post> postagens;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	@ForeignKey(name="KF_BLOG_USER")
	private User dono;
	
	@DateCreated
	private Date dataCriacao;

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
