package net.sf.pms.cdi.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

@ApplicationScoped
public class FacesContextProducer {

	@Produces
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

}
