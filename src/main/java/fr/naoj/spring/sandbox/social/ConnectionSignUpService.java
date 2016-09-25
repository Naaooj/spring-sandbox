package fr.naoj.spring.sandbox.social;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

import fr.naoj.spring.sandbox.model.Profile;
import fr.naoj.spring.sandbox.persistence.UserRepository;

/**
 * @author Johann Bernez
 */
@Component
public class ConnectionSignUpService implements ConnectionSignUp {

	private static final Logger LOG = LoggerFactory.getLogger(ConnectionSignUpService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public String execute(Connection<?> connection) {
		String userId = UUID.randomUUID().toString();
        Profile profile = new Profile(userId, connection.fetchUserProfile(), connection.getImageUrl());
        userRepository.createUser(profile);
        LOG.debug(String.format("User with id [%s] and email [%s] has been created", userId, profile.getUserProfile().getEmail()));
        return userId;
	}

}
