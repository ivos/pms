package net.sf.pms.domain;

import java.io.Serializable;

import org.metawidget.inspector.annotation.UiHidden;

public class UserCriteria implements Serializable {
	private static final long serialVersionUID = 1L;

	private String query;

	@UiHidden
	public String getQueryExpanded() {
		if (null == query) {
			return "%";
		}
		return "%" + query.toLowerCase() + "%";
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((query == null) ? 0 : query.hashCode());
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
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserSearch [query=" + query + "]";
	}

}
