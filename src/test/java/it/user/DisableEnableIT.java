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
public class DisableEnableIT {

	@Test
	public void disable() {
		login("email1", "password1");
		clickLinkWithExactText("User");
		clickLinkWithExactText("email2");
		assertTextInElement("form:status", "Enabled");
		clickLink("form:l_edit");
		assertTextInElement("form:status", "Enabled");
		clickButton("form:b_disable");
		assertTitleEquals("View User");
		assertTextInElement("form:status", "Disabled");
	}

	@Test
	public void enable() {
		login("email1", "password1");
		clickLinkWithExactText("User");
		clickLinkWithExactText("email2");
		assertTextInElement("form:status", "Disabled");
		clickLink("form:l_edit");
		assertTextInElement("form:status", "Disabled");
		clickButton("form:b_enable");
		assertTitleEquals("View User");
		assertTextInElement("form:status", "Enabled");
	}

}
