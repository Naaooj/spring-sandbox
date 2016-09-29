package fr.naoj.spring.sandbox.persistence;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import fr.naoj.spring.sandbox.model.Profile;

/**
 * @author Johann Bernez
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void createUser(Profile profile) {
		jdbcTemplate.update("INSERT into users(username, password, enabled) values(?, ?, true)", profile.getUuid(), passwordEncoder.encode(RandomStringUtils.randomAlphanumeric(8)));
		jdbcTemplate.update("INSERT into authorities(username, authority) values(?, ?)", profile.getUuid(), "ROLE_ADMIN");
        jdbcTemplate.update("INSERT into UserProfile(userId, email, firstName, lastName, name, username, imageUrl) values (?, ?, ?, ?, ?, ?, ?)",
                profile.getUuid(),
                profile.getUserProfile().getEmail(),
                profile.getUserProfile().getFirstName(),
                profile.getUserProfile().getLastName(),
                profile.getUserProfile().getName(),
                profile.getUserProfile().getUsername(),
                profile.getImageUrl());
	}
}
