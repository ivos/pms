package net.sf.pms.support;

public class FullTextColumnBuilder {

	private final StringBuilder sb;

	public FullTextColumnBuilder() {
		sb = new StringBuilder();
	}

	public static FullTextColumnBuilder getInstance() {
		return new FullTextColumnBuilder();
	}

	public FullTextColumnBuilder append(String value) {
		if (null != value) {
			sb.append(' ');
			sb.append(value.toLowerCase());
		}
		return this;
	}

	@Override
	public String toString() {
		return sb.toString().trim();
	}

}
