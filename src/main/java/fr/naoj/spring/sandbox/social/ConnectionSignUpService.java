package fr.naoj.spring.sandbox.social;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.google.api.Google;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.naoj.spring.sandbox.model.Profile;
import fr.naoj.spring.sandbox.persistence.UsersRepository;

/**
 * @author Johann Bernez
 */
@Service
public class ConnectionSignUpService implements ConnectionSignUp {

	private static final Logger LOG = LoggerFactory.getLogger(ConnectionSignUpService.class);
	
	@Autowired
	private UsersRepository userRepository;
	
	@Override
	@Transactional
	public String execute(final Connection<?> connection) {
		final String userId = UUID.randomUUID().toString();
		final SocialType socialType = getSocialType(connection);
        final Profile profile = new Profile(userId, connection.fetchUserProfile(), connection.getImageUrl(), socialType);
        userRepository.createUser(profile);
        LOG.debug(String.format("User with id [%s] and email [%s] has been created", userId, profile.getUserProfile().getEmail()));
        return userId;
	}
	
	private SocialType getSocialType(final Connection<?> connection) {
		if (connection.getApi() instanceof Google) {
			return SocialType.GOOGLE;
		} else if (connection.getApi() instanceof Twitter) {
			return SocialType.TWITTER;
		} else {
			return null;
		}
	}
}
