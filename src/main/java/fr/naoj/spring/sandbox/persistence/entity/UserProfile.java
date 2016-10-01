package fr.naoj.spring.sandbox.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Johann Bernez
 */
@Entity
@Table(name = "userprofile")
public class UserProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "userid", unique = true, nullable = false)
	private String userId;
	
	@Column(name = "firstname", nullable = true)
	private String firstname;
	
	@Column(name = "lastname", nullable = true)
	private String lastname;
	
	@OneToOne(mappedBy = "userProfile")
	@PrimaryKeyJoinColumn
	private Users users;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
}
