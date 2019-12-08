package br.com.aula.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import br.com.aula.model.Comentario;
import br.com.aula.model.Filme;

@Named
@ViewScoped
public class FilmeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private Filme filme;
	private List<Filme> filmes = new ArrayList<Filme>();
	private List<Comentario> comentarios = new ArrayList<Comentario>();
	
	private static final String REST_URI = "http://localhost:8080/api-filmes/v1/filmes";

	private Client client;

	
	@PostConstruct
	public void init() {
		this.client = ClientBuilder.newClient();
		this.buscaFilmes();
	}

	public Filme getFilme() {
		return filme;
	}

	public void cadastrarFilme() {
		if (filme.getId() != null) {
			client.target(REST_URI)
			.path(filme.getId().toString())
			.request(MediaType.APPLICATION_JSON)
			.put(Entity.entity(filme, MediaType.APPLICATION_JSON));
			filme = new Filme();
			this.buscaFilmes();
			FacesContext.getCurrentInstance().addMessage("inputDuracao", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Atualizado com Sucesso!", "Filme Atualizado com Sucesso"));
			return;
		}
		client.target(REST_URI)
		.request(MediaType.APPLICATION_JSON)
		.post(Entity.entity(filme, MediaType.APPLICATION_JSON));
		filme = new Filme();
		this.buscaFilmes();
		FacesContext.getCurrentInstance().addMessage("inputDuracao", new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Cadastrado com Sucesso!", "Filme Cadastrado com Sucesso"));
	}


	public List<Filme> getFilmes() {
		return filmes;
	}
	
	public List<Comentario> getComentarios(){
		return comentarios;
	}
	
	public void buscaFilmes() {
		List<Filme> list =client
		          .target(REST_URI)
		          .request(MediaType.APPLICATION_JSON)
		          .get(Response.class)
		          .readEntity(new GenericType<ArrayList<Filme>>() {});
		this.filmes = list;
	}
	
	public void buscaComentarios(long id) {
		List<Comentario> list =client
		          .target(REST_URI)
		          .path(id + "/comentarios")
		          .request(MediaType.APPLICATION_JSON)
		          .get(Response.class)
		          .readEntity(new GenericType<ArrayList<Comentario>>() {});
		this.comentarios = list;
	}

	public void removerFilme(Long id) {
		Response response = client.target(REST_URI)
		.path(id.toString())
		.request(MediaType.APPLICATION_JSON)
		.delete();
		if(response.getStatus() ==204) {
			this.buscaFilmes();
		}
	}

	public void reset() {
		this.filme = new Filme();
	}
	
	public void alterarFilme(Filme filme) {
		this.filme = filme;
	}
	
}
