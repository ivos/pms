package net.sf.pms.cdi.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import net.sf.pms.cdi.qualifier.Current;
import net.sf.pms.domain.user.User;

import org.jboss.seam.security.Identity;

@ApplicationScoped
public class CurrentUserProducer {

	@Inject
	private Identity identity;

	@Inject
	protected EntityManager entityManager;

	@Produces
	@Current
	public User getCurrentUser() {
		org.picketlink.idm.api.User identityUser = identity.getUser();
		if (null != identityUser) {
			return entityManager
					.createQuery("select u from User u where u.email=:email",
							User.class)
					.setParameter("email", identityUser.getId())
					.getSingleResult();
		}
		throw new IllegalStateException(
				"There is no current user now. Is a user logged in?");
	}

}
