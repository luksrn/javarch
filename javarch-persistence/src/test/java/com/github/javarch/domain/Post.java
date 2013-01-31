package com.github.javarch.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.NotEmpty;

import com.github.javarch.persistence.annotation.DateCreated;
import com.github.javarch.persistence.annotation.LastUpdate;
import com.github.javarch.persistence.orm.hibernate.AbstractPersistable;

@Entity
@AttributeOverride(name="id",column=@Column(name="idPost"))
@SQLDelete(sql="UPDATE post SET ativo = 0 WHERE idPost = ?")
@NamedNativeQueries({
	@NamedNativeQuery(name="Post.exibePost",query="SELECT * FROM post")
})
@SqlResultSetMappings({
	@SqlResultSetMapping(name="Post.exibePost",entities={
			@EntityResult(entityClass=Post.class,fields={@FieldResult(name="titulo",column="titulo")})
			})
})
public class Post extends AbstractPersistable {

	@NotEmpty
	@Column(length=255,nullable=false)
	private String slug;
	
	@NotEmpty
	@Column(length=255,nullable=false)
	private String titulo;
	
	@NotEmpty
	@Lob
	private String conteudo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idBlog")	
	private Blog blog;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUser")
	private User autor;
	
	@DateCreated
	private Date dataPostagem;
	
	@LastUpdate
	private Date ultimaAtualizacao;
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="post")		
	private List<Comment> comentarios;
	
}
