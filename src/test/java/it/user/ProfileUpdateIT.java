package it.user;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import static org.junit.Assert.*;
import net.sf.lightair.LightAir;
import net.sf.lightair.annotation.BaseUrl;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(LightAir.class)
@Setup
@Verify
@BaseUrl("http://localhost:8080/pms-seam3-c")
public class ProfileUpdateIT {

	@Test
	public void ok() {
		beginAt("login");
		setTextField("form:userBeanUserEmail", "email2");
		setTextField("form:userBeanUserPassword", "password2");
		submit();
		assertTextNotPresent("Login failed.");
		assertTextPresent("Logged in as: email2");

		clickLink("_f_login:_l_profile");
		assertEquals("span",
				getElementById("f_profile:currentUserBeanUserEmail").getName());
		setTextField("f_profile:currentUserBeanUserFullName", "Full Name 2_a");
		submit();

		assertTextPresent("Logged in as: email2");
		assertTextFieldEquals("f_profile:currentUserBeanUserFullName",
				"Full Name 2_a");

		setTextField("f_profile:currentUserBeanUserFullName", "Full Name 2_b");
		submit();

		assertTextFieldEquals("f_profile:currentUserBeanUserFullName",
				"Full Name 2_b");
	}

}
