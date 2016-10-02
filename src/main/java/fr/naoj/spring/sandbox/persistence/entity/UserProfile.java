package fr.naoj.spring.sandbox.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import fr.naoj.spring.sandbox.social.SocialType;

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
	
	@Column(name="email")
	private String email;
	
	@Column(name = "firstname", nullable = true)
	private String firstname;
	
	@Column(name = "lastname", nullable = true)
	private String lastname;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "imageurl")
	private String imageUrl;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private Users users;
	
	@Column(name = "socialtype")
	@Enumerated(EnumType.STRING)
	private SocialType socialType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public SocialType getSocialType() {
		return socialType;
	}

	public void setSocialType(SocialType socialType) {
		this.socialType = socialType;
	}
}
