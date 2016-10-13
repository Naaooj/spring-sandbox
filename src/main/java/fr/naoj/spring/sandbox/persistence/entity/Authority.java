package fr.naoj.spring.sandbox.persistence.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Johann Bernez
 */
@Entity
@Table(name = "authorities")
public class Authority implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private AuthorityId id;
	
	@ManyToOne
	@JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false)
	private User user;
	
	public AuthorityId getId() {
		return id;
	}

	public void setId(AuthorityId id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Embeddable
	public static final class AuthorityId implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private String email;
		private String authority;
		
		public AuthorityId() {
			super();
		}
		
		public AuthorityId(String email, String authority) {
			super();
			this.email = email;
			this.authority = authority;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getAuthority() {
			return authority;
		}

		public void setAuthority(String authority) {
			this.authority = authority;
		}
	}
	
}
