package fr.naoj.spring.sandbox.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

import fr.naoj.spring.sandbox.persistence.entity.User;

/**
 * @author Johann Bernez
 */
public class SandboxUser extends SocialUser {

	private static final long serialVersionUID = 1L;
	
	private final UserType type;
	private final String displayName;
	
	public SandboxUser(final User user, final List<GrantedAuthority> authorities) {
		super(user.getEmail(), user.getPassword(), user.getEnabled(), true, true, !user.getLocked(), authorities);
		this.type = user.getType();
		
		switch (this.type) {
			case NATIVE:
				this.displayName = user.getEmail();
				break;
			case GOOGLE:
				this.displayName = user.getUserProfile().getFirstname() + " " + user.getUserProfile().getLastname();
				break;
			case TWITTER:
				this.displayName = user.getUserProfile().getUsername();
				break;
			default:
				this.displayName = user.getEmail();
		}
	}

	public UserType getType() {
		return this.type;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
}
