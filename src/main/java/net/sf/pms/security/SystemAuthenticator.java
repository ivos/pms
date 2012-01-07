package net.sf.pms.security;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import net.sf.pms.domain.user.User;
import net.sf.pms.view.UserBean;

import org.jboss.seam.security.BaseAuthenticator;
import org.picketlink.idm.impl.api.model.SimpleUser;

@ApplicationScoped
public class SystemAuthenticator extends BaseAuthenticator {

	@Inject
	private UserBean userBean;

	@Inject
	private EntityManager entityManager;

	@Override
	public void authenticate() {
		try {
			User user = entityManager
					.createQuery(
							"select user from User user where user.email=:email "
									+ "and user.password=:password and user.status='enabled'",
							User.class)
					.setParameter("email", userBean.getUser().getEmail())
					.setParameter("password", userBean.getUser().getPassword())
					.getSingleResult();
			setStatus(AuthenticationStatus.SUCCESS);
			setUser(new SimpleUser(user.getEmail()));
		} catch (NoResultException e) {
			setStatus(AuthenticationStatus.FAILURE);
		}
	}
}
