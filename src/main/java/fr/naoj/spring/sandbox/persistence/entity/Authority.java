package fr.naoj.spring.sandbox.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

	@Column(name = "authority")
	private String authority;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "username", referencedColumnName = "username")
	private Users users;

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
}
