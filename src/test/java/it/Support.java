package it;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import static org.junit.Assert.*;
import net.sourceforge.jwebunit.api.IElement;

public class Support {

	public static void login(String name, String password) {
		beginAt("login");
		setTextField("form:userBeanUserEmail", name);
		setTextField("form:userBeanUserPassword", password);
		submit();
		assertTextPresent("Logged in as: " + name);
	}

	public static void assertAnchorPresent(String linkId) {
		assertTrue("Unable to find anchor with id [" + linkId + "]",
				null != getAnchorElement(linkId));
	}

	public static void assertAnchorNotPresent(String linkId) {
		assertTrue("Anchor with id [" + linkId + "] found in response",
				null == getAnchorElement(linkId));
	}

	private static IElement getAnchorElement(String linkId) {
		IElement element = getTestingEngine().getElementByID(linkId);
		if (null == element || (!"a".equals(element.getName()))) {
			return null;
		}
		return element;
	}

}
