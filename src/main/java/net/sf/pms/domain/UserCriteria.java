package net.sf.pms.domain;

import java.io.Serializable;

import org.metawidget.inspector.annotation.UiHidden;

public class UserCriteria implements Serializable {
	private static final long serialVersionUID = 1L;

	private String fullText;

	@UiHidden
	public String getFullTextExpanded() {
		if (null == fullText) {
			return "%";
		}
		return "%" + fullText.toLowerCase() + "%";
	}

	public String getFullText() {
		return fullText;
	}

	public void setFullText(String fullText) {
		this.fullText = fullText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fullText == null) ? 0 : fullText.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserCriteria other = (UserCriteria) obj;
		if (fullText == null) {
			if (other.fullText != null)
				return false;
		} else if (!fullText.equals(other.fullText))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserSearch [fullText=" + fullText + "]";
	}

}
