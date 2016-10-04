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
	@JoinColumn(name = "username", referencedColumnName = "username", insertable = false, updatable = false)
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
		
		private String username;
		private String authority;
		
		public AuthorityId() {
			super();
		}
		
		public AuthorityId(String username, String authority) {
			super();
			this.username = username;
			this.authority = authority;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getAuthority() {
			return authority;
		}

		public void setAuthority(String authority) {
			this.authority = authority;
		}
	}
	
}
