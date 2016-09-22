package fr.naoj.spring.sandbox.model;

import org.springframework.social.connect.UserProfile;

/**
 * @author Johann Bernez
 */
public class Profile {

	private final String uuid;
	private final UserProfile userProfile;
	private final String imageUrl;
	
	public Profile(final String uuid, final UserProfile userProfile, final String imageUrl) {
		this.uuid = uuid;
		this.userProfile = userProfile;
		this.imageUrl = imageUrl;
	}

	public String getUuid() {
		return uuid;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public String getImageUrl() {
		return imageUrl;
	}
}
