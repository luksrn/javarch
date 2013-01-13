package com.github.javarch.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.javarch.persistence.annotation.DateCreated;
import com.github.javarch.persistence.annotation.LastUpdate;
import com.github.javarch.persistence.orm.hibernate.AbstractPersistable;

@Entity
@NamedQueries({
	@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
	@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")})
public class User extends AbstractPersistable {	

	private static final long serialVersionUID = -6505315871503702108L;

	@NotEmpty
	@Size(min=3,max=30)
	private String nomeUsuario;
	
	@NotEmpty
	@Size(min=6)
	private String senha;
	
	@NotEmpty
	@Email
	private String email;
	
	@OneToMany(mappedBy="dono",fetch=FetchType.LAZY)	
	@Cascade({CascadeType.SAVE_UPDATE})
	private List<Blog> blogs = new ArrayList<Blog>();
	
	@DateCreated
	private Date dateCreated;
	
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
