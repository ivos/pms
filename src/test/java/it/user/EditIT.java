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
@Verify
@BaseUrl("http://localhost:8080/pms-seam3-c")
public class EditIT {

	@Before
	public void before() {
		beginAt("login");
		setTextField("form:userBeanUserEmail", "email1");
		setTextField("form:userBeanUserPassword", "password1");
		submit();
	}

	@Test
	public void ok() {
		clickLinkWithExactText("User");
		clickLinkWithExactText("email2");
		assertSelectedOptionValuesEqual("form:systemRoles", new String[] {});
		clickLink("form:l_edit");
		assertTitleEquals("Edit User");
		assertSelectedOptionValuesEqual("form:systemRoles", new String[] {});
		selectOptionsByValues("form:systemRoles", new String[] { "user",
				"systemAdmin" });
		clickButton("form:b_save");
		assertTitleEquals("View User");
		assertSelectedOptionValuesEqual("form:systemRoles", new String[] {
				"user", "systemAdmin" });
	}
}
