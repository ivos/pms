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

	public static void assertButtonDisabled(String buttonId) {
		assertButtonPresent(buttonId);
		IElement element = getInputElement(buttonId);
		assertTrue("Button with id [" + buttonId + "] is not disabled",
				"disabled".equals(element.getAttribute("disabled")));
	}

	// internal

	private static IElement getInputElement(String id) {
		return getElement(id, "input");
	}

	private static IElement getAnchorElement(String id) {
		return getElement(id, "a");
	}

	private static IElement getElement(String id, String type) {
		IElement element = getElementById(id);
		if (null == element || (!type.equals(element.getName()))) {
			return null;
		}
		return element;
	}

}
