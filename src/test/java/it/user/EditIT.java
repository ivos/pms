package it.user;

import static it.Support.*;
import static net.sourceforge.jwebunit.junit.JWebUnit.*;
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
public class EditIT {

	@Test
	public void ok() {
		login("email1", "password1");
		clickLinkWithExactText("User");
		clickLinkWithExactText("email2");
		assertSelectedOptionValuesEqual("form:systemRoles", new String[] {});
		clickLink("form:l_edit");
		assertTitleEquals("Edit User");
		assertSelectedOptionValuesEqual("form:systemRoles", new String[] {});
		selectOptionsByValues("form:systemRoles", new String[] { "user",
				"systemAdmin" });
		clickButton("form:b_save");
		assertTitleEquals("Edit User");
		assertSelectedOptionValuesEqual("form:systemRoles", new String[] {
				"user", "systemAdmin" });
	}

	@Test
	@Verify("EditIT.xml")
	public void security_NotAccessibleForUser() {
		login("email3", "password3");
		clickLinkWithExactText("User");
		clickLinkWithExactText("email2");
		assertTextPresent("Name:	Full Name 2");
		assertAnchorNotPresent("form:l_edit");
		assertTextPresent("Edit");
	}

}
