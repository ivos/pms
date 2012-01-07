package net.sf.pms.security;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.pms.cdi.qualifier.Current;
import net.sf.pms.domain.user.SystemRole;
import net.sf.pms.domain.user.User;
import net.sf.pms.security.annotation.SystemAdmin;

import org.jboss.seam.security.annotations.Secures;

@Named
public class SecurityCheck {

	@Inject
	@Current
	private User currentUser;

	@Secures
	@SystemAdmin
	public boolean isAdmin() {
		return currentUser.getSystemRoles().contains(SystemRole.systemAdmin);
	}

}
