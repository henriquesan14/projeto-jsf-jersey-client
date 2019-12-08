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

import br.com.aula.model.Usuario;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Inject
	private Usuario usuario;
	private List<Usuario> usuarios = new ArrayList<Usuario>();
	private static final String REST_URI = "http://localhost:8080/api-filmes/v1/users";
	private Client client;

	
	@PostConstruct
	public void init() {
		this.client = ClientBuilder.newClient();
		this.buscaUsuarios();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void cadastrarUsuario() {
		if (usuario.getId() != null) {
			client.target(REST_URI)
			.path(usuario.getId().toString())
			.request(MediaType.APPLICATION_JSON)
			.put(Entity.entity(usuario, MediaType.APPLICATION_JSON));
			usuario = new Usuario();
			this.buscaUsuarios();
			FacesContext.getCurrentInstance().addMessage("inputDuracao", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Atualizado com Sucesso!", "Usuário Atualizado com Sucesso"));
			return;
		}
		client.target(REST_URI)
		.request(MediaType.APPLICATION_JSON)
		.post(Entity.entity(usuario, MediaType.APPLICATION_JSON));
		usuario = new Usuario();
		this.buscaUsuarios();
		FacesContext.getCurrentInstance().addMessage("inputDuracao", new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Cadastrado com Sucesso!", "Usuário Cadastrado com Sucesso"));
	}


	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	
	public void buscaUsuarios() {
		List<Usuario> list =client
		          .target(REST_URI)
		          .request(MediaType.APPLICATION_JSON)
		          .get(Response.class)
		          .readEntity(new GenericType<ArrayList<Usuario>>() {});
		this.usuarios = list;
	}

	public void removerUsuario(Long id) {
		Response response = client.target(REST_URI)
		.path(id.toString())
		.request(MediaType.APPLICATION_JSON)
		.delete();
		if(response.getStatus() ==204) {
			this.buscaUsuarios();
		}
	}

	public void reset() {
		this.usuario = new Usuario();
	}
	
	public void alterarUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
