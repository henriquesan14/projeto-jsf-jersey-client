package br.com.aula.bean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.aula.model.Usuario;

@ManagedBean
public class LoginBean {

	
	private Usuario usuario = new Usuario();
	private static final String REST_URI = "http://localhost:8080/api-filmes/v1";
	private Client client;

	public Usuario getUsuario() {
		return usuario;
	}
	
	@PostConstruct
	public void init() {
		this.client = ClientBuilder.newClient();
	}
	
	public String efetuarLogin() {
		Response response = client.target(REST_URI)
		.path("login")
		.request(MediaType.APPLICATION_JSON)
		.post(Entity.entity(usuario, MediaType.APPLICATION_JSON));
		Usuario usuarioLogado = response.readEntity(Usuario.class);
		if(response.getStatus() == 200) {
			FacesContext.getCurrentInstance().
			getExternalContext().getSessionMap().put("usuario", usuarioLogado);
			return "pages/cadastroFilme?faces-redirect=true";
		}
		
		FacesContext.getCurrentInstance().addMessage("erroLogin", 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email ou senha Inválidos", "Email ou senha inválidos"));
		
		return null;
	}
	
	public String cadastrar() {
		if(!this.usuario.getSenha().equals(this.usuario.getConfirmSenha())) {
			FacesContext.getCurrentInstance().addMessage("erroCadastro", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha e Confirmação de senha não conferem",
							"Senha e Confirmação de senha não conferem"));
			
			return null;
		}
		this.usuario.setPerfil("USER");
		Response response = client.target(REST_URI)
		.path("register")
		.request(MediaType.APPLICATION_JSON)
		.post(Entity.entity(usuario, MediaType.APPLICATION_JSON));
		if(response.getStatus() == 201) {
			FacesContext.getCurrentInstance().
			getExternalContext().getSessionMap().put("usuario", usuario);
			return "pages/cadastroFilme?faces-redirect=true";
		}
		
		FacesContext.getCurrentInstance().addMessage("erroCadastro", 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cadastrar", "Erro ao cadastrar"));
		
		return null;
	}
	
	public String irCadastrar() {
		return "register?faces-redirect=true";
	}
	
	public String irLogin() {
		return "login?faces-redirect=true";
	}
	
	public String irUsuarios() {
		return "cadastroUsuario?faces-redirect=true";
	}
	
	public String irFilmes() {
		return "cadastroFilme?faces-redirect=true";
	}
	
	public String logout(){
		FacesContext.getCurrentInstance().
		getExternalContext().getSessionMap().put("usuario", null);
		return "login?faces-redirect=true";
	}
	
}
