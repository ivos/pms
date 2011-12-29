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
@Verify("RegisterIT.xml")
@BaseUrl("http://localhost:8080/pms-seam3-c")
public class RegisterIT {

	@Test
	@Verify
	public void ok_Full() {
		// first user becomes systemAdmin
		register("test1@company.com", "Full Name 1", "phone1", "skype1", "1111");
		// all subsequent users get role of plain user
		register("test2@company.com", "Full Name 2", "phone2", "skype2", "2222");
		register("test3@company.com", "Full Name 3", "phone3", "skype3", "3333");
	}

	private void register(String email, String fullName, String phone,
			String skype, String password) {
		beginAt("register");
		setTextField("form:userBeanUserEmail", email);
		setTextField("form:userBeanUserFullName", fullName);
		setTextField("form:userBeanUserPhone", phone);
		setTextField("form:userBeanUserSkype", skype);
		setTextField("form:userBeanUserPassword", password);
		setTextField("form:userBeanUserConfirmPassword", password);
		submit();
	}

	@Test
	@Verify
	public void ok_Minimal() {
		register("email1", "", "", "", "password1");
	}

	@Test
	public void fail_PasswordEmpty() {
		beginAt("register");
		setTextField("form:userBeanUserEmail", "email1");
		setTextField("form:userBeanUserPassword", "");
		setTextField("form:userBeanUserConfirmPassword", "password1");
		submit();
		assertTextPresent("Password: Validation Error: Value is required.");
	}

	@Test
	public void fail_ConfirmPasswordEmpty() {
		beginAt("register");
		setTextField("form:userBeanUserEmail", "email1");
		setTextField("form:userBeanUserPassword", "password1");
		setTextField("form:userBeanUserConfirmPassword", "");
		submit();
		assertTextPresent("Confirm Password: Validation Error: Value is required.");
	}

	@Test
	public void fail_PasswordsNotMatch() {
		beginAt("register");
		setTextField("form:userBeanUserEmail", "email1");
		setTextField("form:userBeanUserPassword", "password1");
		setTextField("form:userBeanUserConfirmPassword", "password2");
		submit();
		assertTextPresent("Passwords must match.");
	}

	@Test
	@Verify("RegisterIT.ok_Minimal-verify.xml")
	public void fail_EmailAlreadyRegistered() {
		register("email1", "", "", "", "password1");
		register("email1", "", "", "", "password2");
		assertTextPresent("E-mail already registered with the system.");
	}

}
