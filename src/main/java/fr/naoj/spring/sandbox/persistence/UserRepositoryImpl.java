package fr.naoj.spring.sandbox.persistence;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import fr.naoj.spring.sandbox.model.Profile;
import fr.naoj.spring.sandbox.persistence.entity.Authority;
import fr.naoj.spring.sandbox.persistence.entity.Authority.AuthorityId;
import fr.naoj.spring.sandbox.persistence.entity.UserProfile;
import fr.naoj.spring.sandbox.persistence.entity.User;

/**
 * @author Johann Bernez
 */
@Repository
public class UserRepositoryImpl implements UserRepositoryExtension {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EntityManager em;
	
	@Override
	public void createUser(Profile profile) {
		final String username = profile.getUuid();
		final String password = passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(8));

		// Creates the user
		final User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setType(profile.getSocialType());
		
		// Creates the profile
		final UserProfile userProfile = new UserProfile();
		userProfile.setUserId(profile.getUuid());
		userProfile.setEmail(profile.getUserProfile().getEmail());
		userProfile.setFirstname(profile.getUserProfile().getFirstName());
		userProfile.setLastname(profile.getUserProfile().getLastName());
		userProfile.setName(profile.getUserProfile().getName());
		userProfile.setUsername(profile.getUserProfile().getUsername());
		userProfile.setImageUrl(profile.getImageUrl());
		
		// Creates the authority
		final Authority auth = new Authority();
		auth.setId(new AuthorityId(username, "ROLE_ADMIN"));
		
		user.setUserProfile(userProfile);
		user.setAuthorities(new ArrayList<>(1));
		user.getAuthorities().add(auth);
		
		this.em.persist(user);
	}
}
