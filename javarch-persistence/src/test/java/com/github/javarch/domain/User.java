package com.github.javarch.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.SQLUpdate;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.javarch.persistence.annotation.DateCreated;
import com.github.javarch.persistence.annotation.LastUpdate;
import com.github.javarch.persistence.orm.hibernate.AbstractPersistable;

@Entity
@AttributeOverride( name="id", column = @Column(name="idUser") )
@NamedQueries({
	@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
	@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")})
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
@SQLUpdate(sql="UPDATE user SET ativo = 0 where idUser = ?")
public class User extends AbstractPersistable {	

	private static final long serialVersionUID = -6505315871503702108L;

	@NotEmpty
	@Size(min=3,max=30)
	@Column(name="nomeusuario")
	private String nomeUsuario;
	
	@NotEmpty
	@Size(min=6)
	private String senha;
	
	@NotEmpty
	@Email
	private String email;
	
	@OneToMany(mappedBy="dono",fetch=FetchType.LAZY)	
	@Cascade({CascadeType.SAVE_UPDATE})
	@BatchSize(size=10)
	@OrderBy("dataCriacao DESC")
	private List<Blog> blogs = new ArrayList<Blog>();
	
	@Column(name="DATA_CRIACAO")
	@DateCreated
	private Date dateCreated;
	
	@Column(name="DATA_ATUALIZACAO")
	@LastUpdate
	private Date lastUpdated;
	
	public User() {
		super();
	}

	public User(String nomeUsuario, String senha, String email) {
		super();
		this.nomeUsuario = nomeUsuario;
		this.senha = senha;
		this.email = email;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}
	
	
}
