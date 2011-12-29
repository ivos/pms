package net.sf.pms.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ForeignKey;
import org.metawidget.inspector.annotation.UiComesAfter;
import org.metawidget.inspector.annotation.UiHidden;
import org.metawidget.inspector.annotation.UiMasked;

@Entity
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false)
	private Long id;

	@Version
	private int version;

	@NotNull
	@Size(max = 100)
	@Column(unique = true, updatable = false)
	private String email;

	@Size(max = 100)
	@UiComesAfter("email")
	private String fullName;

	@Size(max = 30)
	@UiComesAfter("fullName")
	private String phone;

	@Size(max = 100)
	@UiComesAfter("phone")
	private String skype;

	@NotNull
	@Size(min = 4, max = 100)
	@UiMasked
	@UiComesAfter("skype")
	private String password;

	@Transient
	@NotNull
	@Size(min = 4, max = 100)
	@UiMasked
	@UiComesAfter("password")
	private String confirmPassword;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	@Column(name = "system_role", length = 32, nullable = false)
	@ForeignKey(name = "User_System_Roles__User")
	@UiHidden
	private Set<SystemRole> systemRoles = new HashSet<SystemRole>();

	@Size(max = 200)
	@UiHidden
	private String fullText;

	// business logic

	/**
	 * @return <code>true</code> iff password matches confirmPassword
	 */
	public boolean doPasswordsMatch() {
		return null != password && password.equals(confirmPassword);
	}

	@PrePersist
	@PreUpdate
	protected void recreateFullText() {
		StringBuilder sb = new StringBuilder();
		sb.append(email.toLowerCase());
		if (null != fullName) {
			sb.append(' ');
			sb.append(fullName.toLowerCase());
		}
		if (null != phone) {
			sb.append(' ');
			sb.append(phone.toLowerCase());
		}
		if (null != skype) {
			sb.append(' ');
			sb.append(skype.toLowerCase());
		}
		fullText = sb.toString();
	}

	// getters, setters

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Set<SystemRole> getSystemRoles() {
		return systemRoles;
	}

	public void setSystemRoles(Set<SystemRole> systemRoles) {
		this.systemRoles = systemRoles;
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
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", version=" + version + ", email=" + email
				+ ", fullName=" + fullName + ", phone=" + phone + ", skype="
				+ skype + ", password=" + password + ", confirmPassword="
				+ confirmPassword + ", systemRoles=" + systemRoles
				+ ", fullText=" + fullText + "]";
	}

}