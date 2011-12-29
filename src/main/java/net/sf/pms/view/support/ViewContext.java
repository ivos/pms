package net.sf.pms.view.support;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class ViewContext {

	@Inject
	private FacesContext facesContext;

	public void addErrorMessage(String messageText) {
		addErrorMessage(null, messageText);
	}

	public void addErrorMessage(String elementId, String messageText) {
		facesContext.addMessage(elementId, new FacesMessage(
				FacesMessage.SEVERITY_ERROR, messageText, null));
	}

	public void addInfoMessage(String messageText) {
		addInfoMessage(messageText);
	}

	public void addInfoMessage(String elementId, String messageText) {
		facesContext.addMessage(elementId, new FacesMessage(
				FacesMessage.SEVERITY_INFO, messageText, null));
	}

}
