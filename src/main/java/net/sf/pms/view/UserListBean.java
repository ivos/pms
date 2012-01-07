package net.sf.pms.view;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import net.sf.pms.domain.user.User;
import net.sf.pms.domain.user.UserCriteria;

import org.jboss.logging.Logger;
import org.jboss.seam.security.annotations.LoggedIn;
import org.metawidget.forge.navigation.MenuItem;
import org.metawidget.forge.persistence.PersistenceUtil;

@Named
@RequestScoped
public class UserListBean extends PersistenceUtil implements MenuItem {
	private static final long serialVersionUID = 1L;

	private List<User> list = null;
	private Integer itemsCount;
	private UserCriteria criteria = new UserCriteria();

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

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

	public String search() {
		return "list?faces-redirect=true&includeViewParams=true";
	}

	public List<User> executeSearchQuery(int firstResult, int maxResults) {
		log.infov("search, {0}", criteria);
		return getEntityManager()
				.createQuery(
						"select user from User user where user.fullText like :fullText",
						User.class)
				.setParameter("fullText", criteria.getQueryExpanded())
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public int executeCountQuery() {
		return getEntityManager()
				.createQuery(
						"select count(*) from User user where user.fullText like :fullText",
						Long.class)
				.setParameter("fullText", criteria.getQueryExpanded())
				.getSingleResult().intValue();
	}

	public UserCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(UserCriteria criteria) {
		this.criteria = criteria;
	}

	@LoggedIn
	public List<User> getList() {
		if (null == list && !facesContext.isPostback()) {
			list = executeSearchQuery(getPageFirstItem(), getPageSize());
		}
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}

	// pagination

	private final int pageSize = 10;
	private int page;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public boolean isNextAvailable() {
		return (getPage() + 1) * pageSize + 1 <= getItemsCount();
	}

	public boolean isPreviousAvailable() {
		return page > 0;
	}

	public int getItemsCount() {
		if (null == itemsCount && !facesContext.isPostback()) {
			itemsCount = executeCountQuery();
		}
		return itemsCount;
	}

	public void setItemsCount(int itemsCount) {
		this.itemsCount = itemsCount;
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
				+ ", itemsCount=" + itemsCount + ", criteria=" + criteria
				+ ", pageSize=" + pageSize + ", page=" + page + "]";
	}

}
