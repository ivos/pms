package net.sf.pms.security;

import net.sf.pms.cdi.qualifier.Current;
import net.sf.pms.domain.SystemRole;
import net.sf.pms.domain.User;
import net.sf.pms.security.annotation.SystemAdmin;

import org.jboss.seam.security.annotations.Secures;

public class SecurityCheck {

	@Secures
	@SystemAdmin
	public boolean isAdmin(@Current User user) {
		return user.getSystemRoles().contains(SystemRole.systemAdmin);
	}

}
