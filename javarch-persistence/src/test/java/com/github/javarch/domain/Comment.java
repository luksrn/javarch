package com.github.javarch.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.SQLDelete;

import com.github.javarch.persistence.annotation.DateCreated;
import com.github.javarch.persistence.annotation.LastUpdate;
import com.github.javarch.persistence.orm.hibernate.AbstractPersistable;

@Entity
@AttributeOverride(name="id",column=@Column(name="idComentario"))
@SQLDelete(sql="UPDATE comment SET ativo = 0 WHERE idComentario = ?")
public class Comment extends AbstractPersistable {
	
	@ManyToOne
	@JoinColumn(name="idUser")
	private User autor;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idPost")
	private Post post;
	
	@Lob
	private String comentario;
	
	@DateCreated
	private Date dataCriacao;
	
	@LastUpdate
	private Date ultimaAtualizacao;

}
