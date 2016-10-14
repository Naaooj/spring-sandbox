package fr.naoj.spring.sandbox.model;

import org.springframework.social.connect.UserProfile;

/**
 * @author Johann Bernez
 */
abstract class AbstractProfile implements Profile {

	private final String email;
	private final UserProfile userProfile;
	private final String imageUrl;
	private final UserType socialType;
	
	public AbstractProfile(final String email, final UserProfile userProfile, final String imageUrl, final UserType socialType) {
		this.email = email;
		this.userProfile = userProfile;
		this.imageUrl = imageUrl;
		this.socialType = socialType;
	}

	public String getEmail() {
		return email;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	
	public UserType getSocialType() {
		return socialType;
	}
}
