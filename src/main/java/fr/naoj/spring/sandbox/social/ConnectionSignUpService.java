package fr.naoj.spring.sandbox.social;

import java.util.UUID;

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

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public String execute(Connection<?> connection) {
		String userId = UUID.randomUUID().toString();
        Profile profile = new Profile(userId, connection.fetchUserProfile(), connection.getImageUrl());
        userRepository.createUser(profile);
        return userId;
	}

}
