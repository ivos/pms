package net.sf.pms.view;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.sf.pms.cdi.qualifier.Current;
import net.sf.pms.domain.User;
import net.sf.pms.view.support.ViewContext;

import org.jboss.seam.transaction.Transactional;
import org.metawidget.forge.persistence.PersistenceUtil;

@Transactional
@Named
@RequestScoped
public class CurrentUserBean extends PersistenceUtil {
	private static final long serialVersionUID = 1L;

	@Inject
	@Current
	private User user;

	@Inject
	private transient ViewContext viewContext;

	public void saveProfile() {
		// passwords not used on the form,
		// comply with Hibernate bean validation:
		user.setConfirmPassword(user.getPassword());

		save(user);
		viewContext.addInfoMessage("f_profile", "Profile updated.");
	}

	public void changePassword() {
		if (!user.doPasswordsMatch()) {
			viewContext.addErrorMessage(
					"f_password:currentUserBeanUserConfirmPassword",
					"Passwords must match.");
			return;
		}
		save(user);
		viewContext.addInfoMessage("f_password", "Password changed.");
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
