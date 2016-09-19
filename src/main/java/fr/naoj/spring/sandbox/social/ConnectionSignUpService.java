package fr.naoj.spring.sandbox.social;

import java.util.UUID;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @author Johann Bernez
 */
@Component
public class ConnectionSignUpService implements ConnectionSignUp {

	@Override
	public String execute(Connection<?> connection) {
		String userId = UUID.randomUUID().toString();
        connection.fetchUserProfile();
        return userId;
	}

}
