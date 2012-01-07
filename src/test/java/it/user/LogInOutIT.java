package it.user;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import net.sf.lightair.LightAir;
import net.sf.lightair.annotation.BaseUrl;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(LightAir.class)
@Setup
@Verify("LogInOutIT.xml")
@BaseUrl("http://localhost:8080/pms-seam3-c")
public class LogInOutIT {

	@Test
	public void ok() {
		beginAt("login");
		assertLinkPresentWithExactText("Log in...");
		setTextField("form:userBeanUserEmail", "email2");
		setTextField("form:userBeanUserPassword", "password2");
		submit();
		assertTextNotPresent("Login failed.");
		assertTextPresent("Logged in as: email2");

		clickLinkWithExactText("Log out");
		assertLinkNotPresentWithExactText("Log out");
		assertLinkPresentWithExactText("Log in...");
		// takes to home page to prevent errors
		assertTitleEquals("Welcome");
	}

	@Test
	public void fail_Email() {
		beginAt("login");
		assertLinkPresentWithExactText("Log in...");
		setTextField("form:userBeanUserEmail", "nonExistent");
		setTextField("form:userBeanUserPassword", "password2");
		submit();
		assertTextPresent("Login failed.");
		assertTitleEquals("Login");
		assertTextFieldEquals("form:userBeanUserEmail", "nonExistent");
		assertTextFieldEquals("form:userBeanUserPassword", "");
	}

	@Test
	public void fail_Password() {
		beginAt("login");
		assertLinkPresentWithExactText("Log in...");
		setTextField("form:userBeanUserEmail", "email2");
		setTextField("form:userBeanUserPassword", "nonExistent");
		submit();
		assertTextPresent("Login failed.");
		assertTitleEquals("Login");
		assertTextFieldEquals("form:userBeanUserEmail", "email2");
		assertTextFieldEquals("form:userBeanUserPassword", "");
	}

	@Test
	public void fail_UserDisabled() {
		beginAt("login");
		assertLinkPresentWithExactText("Log in...");
		setTextField("form:userBeanUserEmail", "email3");
		setTextField("form:userBeanUserPassword", "password3");
		submit();
		assertTextPresent("Login failed.");
		assertTitleEquals("Login");
		assertTextFieldEquals("form:userBeanUserEmail", "email3");
		assertTextFieldEquals("form:userBeanUserPassword", "");
	}

}
