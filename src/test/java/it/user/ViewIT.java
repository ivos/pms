package it.user;

import static it.Support.*;
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
@Verify("ViewIT.xml")
@BaseUrl("http://localhost:8080/pms-seam3-c")
public class ViewIT {

	@Before
	public void before() {
		login("email1", "password1");
	}

	@Test
	public void ok() {
		clickLinkWithExactText("User");
		clickLinkWithExactText("email2");
		assertTitleEquals("View User");
		assertTextPresent("Viewing user");
		assertTextPresent("E-mail:	email2");
		assertTextPresent("Name:	Full Name 2");
		assertTextPresent("Phone:	phone2");
		assertTextPresent("Skype:	skype2");
		assertTextPresent("Status:	Enabled");
		assertTextPresent("System Roles:");
		assertSelectedOptionValuesEqual("form:systemRoles",
				new String[] { "user" });
		clickLink("form:l_view_all");
		assertTitleEquals("List Users");
	}

}
