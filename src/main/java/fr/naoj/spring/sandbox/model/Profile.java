package fr.naoj.spring.sandbox.model;

import org.springframework.social.connect.UserProfile;

import fr.naoj.spring.sandbox.social.SocialType;

/**
 * @author Johann Bernez
 */
public class Profile {

	private final String uuid;
	private final UserProfile userProfile;
	private final String imageUrl;
	private final SocialType socialType;
	
	public Profile(final String uuid, final UserProfile userProfile, final String imageUrl, final SocialType socialType) {
		this.uuid = uuid;
		this.userProfile = userProfile;
		this.imageUrl = imageUrl;
		this.socialType = socialType;
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
	
	public SocialType getSocialType() {
		return socialType;
	}
}
