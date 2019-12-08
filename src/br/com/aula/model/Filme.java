package br.com.aula.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



public class Filme implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String titulo;
	
	private String sinopse;
	
	private BigDecimal nota;
	
	
	private String urlImagem;
	
	
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	public Filme() {
		super();
	}

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getTitulo() {
		return titulo;
	}



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	public String getSinopse() {
		return sinopse;
	}



	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}



	public BigDecimal getNota() {
		return nota;
	}



	public void setNota(BigDecimal nota) {
		this.nota = nota;
	}



	public String getUrlImagem() {
		return urlImagem;
	}



	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}



	public List<Comentario> getComentarios() {
		return comentarios;
	}



	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

}
