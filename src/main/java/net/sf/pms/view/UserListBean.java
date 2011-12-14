package net.sf.pms.view;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import net.sf.pms.domain.User;
import net.sf.pms.domain.UserCriteria;

import org.jboss.logging.Logger;
import org.metawidget.forge.navigation.MenuItem;
import org.metawidget.forge.persistence.PersistenceUtil;

@Named
@RequestScoped
public class UserListBean extends PersistenceUtil implements MenuItem {
	private static final long serialVersionUID = 1L;

	private List<User> list = null;
	private Integer itemsCount;
	private UserCriteria userCriteria = new UserCriteria();

	@Inject
	private Logger log;

	@Override
	public Class<?> getItemType() {
		return User.class;
	}

	@Override
	public String getLiteralPath() {
		return "/page/user/list";
	}

	@Override
	public String getLabel() {
		return null;
	}

	public void search() {
		list = executeSearchQuery(getPageFirstItem(), getPageSize());
		itemsCount = executeCountQuery();
	}

	public List<User> executeSearchQuery(int firstResult, int maxResults) {
		log.infov("search, {0}", userCriteria);
		return getEntityManager()
				.createQuery(
						"select user from User user where user.fullText like :fullText",
						User.class)
				.setParameter("fullText", userCriteria.getFullTextExpanded())
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public int executeCountQuery() {
		return getEntityManager()
				.createQuery(
						"select count(*) from User user where user.fullText like :fullText",
						Long.class)
				.setParameter("fullText", userCriteria.getFullTextExpanded())
				.getSingleResult().intValue();
	}

	public UserCriteria getUserCriteria() {
		return userCriteria;
	}

	public void setUserCriteria(UserCriteria userCriteria) {
		this.userCriteria = userCriteria;
	}

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}

	// pagination

	private final int pageSize = 10;
	private Integer page;

	public int getPage() {
		return (null == page) ? 0 : page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public boolean isNextAvailable() {
		System.out.println("isNextAvailable " + this);
		return (null == itemsCount)
				|| ((getPage() + 1) * pageSize + 1 <= getItemsCount());
	}

	public boolean isPreviousAvailable() {
		System.out.println("isPreviousAvailable " + this);
		return (null == page) || (page > 0);
	}

	public int getItemsCount() {
		return (null == itemsCount) ? 0 : itemsCount;
	}

	public void setItemsCount(int itemsCount) {
		this.itemsCount = itemsCount;
	}

	public void nextPage() {
		System.out.println("nextPage in " + this);
		if (isNextAvailable()) {
			page++;
		}
		search();
		System.out.println("nextPage out " + this);
	}

	public void previousPage() {
		System.out.println("previousPage in " + this);
		if (isPreviousAvailable()) {
			page--;
		}
		search();
		System.out.println("previousPage out " + this);
	}

	public int getPageFirstItem() {
		return getPage() * pageSize;
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

	@Override
	public String toString() {
		return "UserListBean [list=" + (null == list ? null : list.size())
				+ ", itemsCount=" + itemsCount + ", userCriteria="
				+ userCriteria + ", pageSize=" + pageSize + ", page=" + page
				+ "]";
	}

}
