package br.com.aula.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.jboss.jandex.TypeTarget.Usage;

import br.com.aula.model.Usuario;

public class LoginPhaseListener implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		
		FacesContext context = event.getFacesContext();
		
		String pagina = context.getViewRoot().getViewId();
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
		if(pagina.equals("/login.xhtml") || pagina.equals("/register.xhtml")) {
			return;
		}
		if(pagina.equals("/pages/cadastroUsuario.xhtml") && usuarioLogado != null && !usuarioLogado.getPerfil().equals("ADMIN")) {
			NavigationHandler handler = context.getApplication().getNavigationHandler();
			handler.handleNavigation(context, null, "/pages/cadastroFilme?faces-redirect=true");
			context.renderResponse();
			return;
		}
		if(context.getExternalContext().getSessionMap().get("usuario") != null) {
			return;
		}
		
		
		
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "/login?faces-redirect=true");
		context.renderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
