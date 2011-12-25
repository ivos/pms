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
@Verify("ListIT.xml")
@BaseUrl("http://localhost:8080/pms-seam3-c")
public class ListIT {

	@Before
	public void before() {
		beginAt("login");
		setTextField("form:userBeanUserEmail", "email4");
		setTextField("form:userBeanUserPassword", "password4");
		submit();
	}

	@Test
	public void paging_NoCriteria() {
		clickLinkWithExactText("User");
		assertLinkPresentWithExactText("email1");
		assertLinkPresentWithExactText("email2");
		assertLinkPresentWithExactText("email10");
		assertLinkNotPresentWithExactText("email11");
		// assertLinkNotPresent("form:l_previous_page");
		clickLink("form:l_next_page");
		assertLinkPresentWithExactText("email11");
		assertLinkPresentWithExactText("email12");
		assertLinkPresentWithExactText("email20");
		assertLinkNotPresentWithExactText("email1");
		assertLinkNotPresentWithExactText("email10");
		assertLinkNotPresentWithExactText("email21");
		clickLink("form:l_next_page");
		assertLinkPresentWithExactText("email21");
		assertLinkPresentWithExactText("email22");
		assertLinkNotPresentWithExactText("email20");
		// assertLinkNotPresent("form:l_next_page");
		clickLink("form:l_previous_page");
		assertLinkPresentWithExactText("email11");
		assertLinkPresentWithExactText("email20");
		assertLinkNotPresentWithExactText("email10");
		assertLinkNotPresentWithExactText("email21");
		clickLink("form:l_previous_page");
		assertLinkPresentWithExactText("email1");
		assertLinkPresentWithExactText("email10");
		assertLinkNotPresentWithExactText("email11");
		// assertLinkNotPresent("form:l_previous_page");
	}

	@Test
	public void paging_WithCriteria() {
		clickLinkWithExactText("User");
		assertLinkPresentWithExactText("email1");
		assertLinkPresentWithExactText("email2");
		assertLinkPresentWithExactText("email9");
		assertLinkPresentWithExactText("email10");
		setTextField("form:userListBeanCriteriaQuery", "1");
		submit();
		assertLinkPresentWithExactText("email1");
		assertLinkPresentWithExactText("email10");
		assertLinkPresentWithExactText("email11");
		assertLinkPresentWithExactText("email18");
		assertLinkNotPresentWithExactText("email2");
		assertLinkNotPresentWithExactText("email9");
		assertLinkNotPresentWithExactText("email19");
		// assertLinkNotPresent("form:l_previous_page");
		clickLink("form:l_next_page");
		assertLinkPresentWithExactText("email19");
		assertLinkPresentWithExactText("email21");
		assertLinkNotPresentWithExactText("email18");
		assertLinkNotPresentWithExactText("email20");
		assertLinkNotPresentWithExactText("email22");
		assertTextFieldEquals("form:userListBeanCriteriaQuery", "1");
		// assertLinkNotPresent("form:l_next_page");
		clickLink("form:l_previous_page");
		assertLinkPresentWithExactText("email1");
		assertLinkPresentWithExactText("email18");
		assertLinkNotPresentWithExactText("email2");
		assertLinkNotPresentWithExactText("email19");
		assertTextFieldEquals("form:userListBeanCriteriaQuery", "1");
		// assertLinkNotPresent("form:l_previous_page");
	}

}
