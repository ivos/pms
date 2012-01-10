package net.sf.pms.view;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import net.sf.pms.domain.user.User;
import net.sf.pms.domain.user.UserCriteria;

import org.jboss.seam.security.annotations.LoggedIn;
import org.metawidget.forge.navigation.MenuItem;
import org.metawidget.forge.persistence.PaginationHelper;
import org.metawidget.forge.persistence.PersistenceUtil;

@Named
@RequestScoped
public class UserListBean extends PersistenceUtil implements MenuItem {
	private static final long serialVersionUID = 1L;

	private List<User> list = null;
	private Integer itemsCount;
	private PaginationHelper<User> pagination;
	private final UserCriteria criteria = new UserCriteria();

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
		pagination.setPage(0);
		return "list?faces-redirect=true&includeViewParams=true";
	}

	private List<User> executeSearchQuery(int firstResult, int maxResults) {
		return getEntityManager()
				.createQuery("select user" + QUERY, User.class)
				.setParameter("fullText", criteria.getQueryExpanded())
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	private int executeCountQuery() {
		return getEntityManager()
				.createQuery("select count(*)" + QUERY, Long.class)
				.setParameter("fullText", criteria.getQueryExpanded())
				.getSingleResult().intValue();
	}

	private static final String QUERY = " from User user where user.fullText like :fullText";

	public UserCriteria getCriteria() {
		return criteria;
	}

	@LoggedIn
	public List<User> getList() {
		if (null == list && !facesContext.isPostback()) {
			list = pagination.createPageDataModel();
		}
		return list;
	}

	public PaginationHelper<User> getPagination() {
		if (pagination == null) {
			pagination = new PaginationHelper<User>(10) {
				@Override
				public int getItemsCount() {
					if (null == itemsCount && !facesContext.isPostback()) {
						itemsCount = executeCountQuery();
					}
					return itemsCount;
				}

				@Override
				public List<User> createPageDataModel() {
					return executeSearchQuery(getPageFirstItem(), getPageSize());
				}
			};
		}
		return pagination;
	}

	@Override
	public String toString() {
		return "UserListBean [list=" + (null == list ? null : list.size())
				+ ", itemsCount=" + itemsCount + ", pagination=" + pagination
				+ ", criteria=" + criteria + "]";
	}

}
