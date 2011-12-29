package net.sf.pms.view.support;

import javax.inject.Inject;

import org.jboss.seam.exception.control.CaughtException;
import org.jboss.seam.exception.control.Handles;
import org.jboss.seam.exception.control.HandlesExceptions;
import org.jboss.seam.security.AuthorizationException;

@HandlesExceptions
public class ExceptionHandler {

	@Inject
	ViewContext viewContext;

	public void handleAuthorizationException(
			@Handles CaughtException<AuthorizationException> event) {
		viewContext
				.addErrorMessage("You are not authorized to perform the action requested.");
		event.handled();
		System.out
				.println("================== FANTOMAS!!!! ===================");
	}

	// public void handleException(@Handles CaughtException<Exception> event) {
	// Throwable rootCause = event.getException();
	// while (null != rootCause.getCause()) {
	// rootCause = rootCause.getCause();
	// }
	// if (rootCause instanceof AuthorizationException) {
	// viewContext
	// .addErrorMessage("You are not authorized to perform the action requested.");
	// event.handled();
	// }
	// }

}
