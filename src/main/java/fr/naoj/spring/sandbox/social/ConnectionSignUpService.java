package fr.naoj.spring.sandbox.social;

import fr.naoj.spring.sandbox.model.EnabledProfile;
import fr.naoj.spring.sandbox.model.Profile;
import fr.naoj.spring.sandbox.model.UserType;
import fr.naoj.spring.sandbox.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.google.api.Google;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author Johann Bernez
 */
@Service
public class ConnectionSignUpService implements ConnectionSignUp {

	private static final Logger LOG = LoggerFactory.getLogger(ConnectionSignUpService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional
	public String execute(final Connection<?> connection) {
		final String userId = UUID.randomUUID().toString();
		final UserType socialType = getSocialType(connection);
        final Profile profile = new EnabledProfile(userId, connection.fetchUserProfile(), connection.getImageUrl(), socialType);
        userRepository.createUser(profile);
        LOG.debug(String.format("User with id [%s] and email [%s] has been created", userId, profile.getUserProfile().getEmail()));
        return userId;
	}
	
	private UserType getSocialType(final Connection<?> connection) {
		if (connection.getApi() instanceof Google) {
			return UserType.GOOGLE;
		} else if (connection.getApi() instanceof Twitter) {
			return UserType.TWITTER;
		} else {
			return null;
		}
	}
}
