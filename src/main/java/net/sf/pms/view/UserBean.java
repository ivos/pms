package net.sf.pms.view;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import net.sf.pms.domain.SystemRole;
import net.sf.pms.domain.User;
import net.sf.pms.security.annotation.SystemAdmin;
import net.sf.pms.view.support.ViewContext;

import org.jboss.logging.Logger;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.LoggedIn;
import org.jboss.seam.transaction.Transactional;
import org.metawidget.forge.persistence.PersistenceUtil;

@Named
@RequestScoped
public class UserBean extends PersistenceUtil {
	private static final long serialVersionUID = 1L;

	private User user = new User();
	private long id = 0;

	@Inject
	private Logger log;

	@Inject
	private transient ViewContext viewContext;

	@Inject
	Identity identity;

	@Transactional
	public String register() {
		if (!user.doPasswordsMatch()) {
			viewContext.addErrorMessage("form:userBeanUserConfirmPassword",
					"Passwords must match.");
			return null;
		}
		if (isEmailAlreadyRegistered()) {
			viewContext.addErrorMessage("form:userBeanUserEmail",
					"E-mail already registered with the system.");
			return null;
		}
		boolean isFirstUserInSystem = findAll(User.class, 0, 1).isEmpty();
		create(user);
		if (isFirstUserInSystem) {
			log.info("Creating first user in system, assigning role systemAdmin.");
			user.getSystemRoles().add(SystemRole.systemAdmin);
		} else {
			log.info("Assigning role user.");
			user.getSystemRoles().add(SystemRole.user);
		}
		return "login?faces-redirect=true";
	}

	private boolean isEmailAlreadyRegistered() {
		return getEntityManager()
				.createQuery(
						"select user from User user where user.email=:email",
						User.class).setParameter("email", user.getEmail())
				.getResultList().size() > 0;
	}

	public String login() {
		if (identity.login() == Identity.RESPONSE_LOGIN_SUCCESS) {
			return "list?faces-redirect=true";
		}
		viewContext.addErrorMessage("Login failed.");
		return null;
	}

	public String logout() {
		identity.logout();
		return "/index";
	}

	@Transactional
	public String generate() {
		for (int i = 0; i < 121; i++) {
			User user = new User();
			user.setEmail("email" + i + "@bla.com");
			user.setFullName("fullName" + i);
			user.setPhone("543-678-" + i);
			user.setSkype("name.surname" + i);
			user.setPassword("qqqq");
			user.setConfirmPassword(user.getPassword());
			user.getSystemRoles().add(SystemRole.user);
			create(user);
		}
		return "list?faces-redirect=true";
	}

	public void load() {
		user = findById(User.class, id);
	}

	@Transactional
	public String create() {
		create(user);
		return "view?faces-redirect=true&id=" + user.getId();
	}

	@Transactional
	public String delete() {
		delete(user);
		return "list?faces-redirect=true";
	}

	@Transactional
	@SystemAdmin
	public String save() {
		user.setConfirmPassword(user.getPassword());
		save(user);
		return "view?faces-redirect=true&id=" + user.getId();
	}

	public long getId() {
		return id;
	}

	@LoggedIn
	public void setId(long id) {
		this.id = id;
		if (id > 0) {
			load();
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<SelectItem> getSystemRoles() {
		return Arrays.asList(new SelectItem(SystemRole.user, "User"),
				new SelectItem(SystemRole.systemAdmin, "System administrator"));
	}
}