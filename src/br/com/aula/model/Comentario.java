package br.com.aula.model;

import java.io.Serializable;

public class Comentario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String mensagem;
	private int nota;
	
	private Filme filme;
	
	
	public Comentario() {
		super();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public int getNota() {
		return nota;
	}
	public void setNota(int nota) {
		this.nota = nota;
	}
	public Filme getFilme() {
		return filme;
	}
	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	
}
