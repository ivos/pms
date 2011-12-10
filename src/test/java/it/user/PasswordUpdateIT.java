package it.user;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import net.sf.lightair.LightAir;
import net.sf.lightair.annotation.BaseUrl;
import net.sf.lightair.annotation.Setup;
import net.sf.lightair.annotation.Verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(LightAir.class)
@Setup
@Verify("PasswordUpdateIT.xml")
@BaseUrl("http://localhost:8080/pms-seam3-c")
public class PasswordUpdateIT {

	@Before
	public void login() {
		beginAt("login");
		setTextField("form:userBeanUserEmail", "email2");
		setTextField("form:userBeanUserPassword", "password2");
		submit();
		assertTextNotPresent("Login failed.");
		assertTextPresent("Logged in as: email2");
	}

	@Test
	@Verify
	public void ok() {
		clickLink("_f_login:_l_profile");
		setTextField("f_password:currentUserBeanUserPassword", "password2_a");
		setTextField("f_password:currentUserBeanUserConfirmPassword",
				"password2_a");
		submit();
	}

	@Test
	public void fail_PasswordEmpty() {
		clickLink("_f_login:_l_profile");
		setTextField("f_password:currentUserBeanUserPassword", "");
		setTextField("f_password:currentUserBeanUserConfirmPassword",
				"password2_a");
		submit();
		assertTextPresent("Password: Validation Error: Value is required.");
	}

	@Test
	public void fail_ConfirmPasswordEmpty() {
		clickLink("_f_login:_l_profile");
		setTextField("f_password:currentUserBeanUserPassword", "password2_a");
		setTextField("f_password:currentUserBeanUserConfirmPassword", "");
		submit();
		assertTextPresent("Confirm password: Validation Error: Value is required.");
	}

	@Test
	public void fail_PasswordsNotMatch() {
		clickLink("_f_login:_l_profile");
		setTextField("f_password:currentUserBeanUserPassword", "password2_a");
		setTextField("f_password:currentUserBeanUserConfirmPassword",
				"password2_b");
		submit();
		assertTextPresent("Passwords must match.");
	}

}
