package net.sf.pms.view.support;

import java.util.List;

public abstract class PaginationHelper<T> {

	private final int pageSize;
	private int page;

	public PaginationHelper(int pageSize) {
		this.pageSize = pageSize;
	}

	public abstract int getItemsCount();

	public abstract List<T> createPageDataModel();

	public int getPageFirstItem() {
		return page * pageSize;
	}

	public int getPageLastItem() {
		int i = getPageFirstItem() + pageSize - 1;
		int count = getItemsCount() - 1;
		if (i > count) {
			i = count;
		}
		if (i < 0) {
			i = 0;
		}
		return i;
	}

	public boolean isNextAvailable() {
		System.out.println("isNextAvailable " + this);
		return (page + 1) * pageSize + 1 <= getItemsCount();
	}

	public boolean isPreviousAvailable() {
		System.out.println("isPreviousAvailable " + this);
		return page > 0;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void nextPage() {
		System.out.println("nextPage in " + this);
		if (isNextAvailable()) {
			page++;
		}
		System.out.println("nextPage out " + this);
	}

	public void previousPage() {
		System.out.println("previousPage in " + this);
		if (isPreviousAvailable()) {
			page--;
		}
		System.out.println("previousPage out " + this);
	}

	public void firstPage() {
		page = 0;
	}

	@Override
	public String toString() {
		return "PaginationHelper [pageSize=" + pageSize + ", page=" + page
				+ "]";
	}

}
