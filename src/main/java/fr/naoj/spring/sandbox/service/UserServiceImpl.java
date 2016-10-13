package fr.naoj.spring.sandbox.service;

import fr.naoj.spring.sandbox.model.Profile;
import fr.naoj.spring.sandbox.model.Signup;
import fr.naoj.spring.sandbox.model.TokenStatus;
import fr.naoj.spring.sandbox.model.UserType;
import fr.naoj.spring.sandbox.persistence.RegistrationTokenRepository;
import fr.naoj.spring.sandbox.persistence.UserProfileRepository;
import fr.naoj.spring.sandbox.persistence.UserRepository;
import fr.naoj.spring.sandbox.persistence.entity.RegistrationToken;
import fr.naoj.spring.sandbox.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Johann Bernez
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private RegistrationTokenRepository registrationTokenRepository;

	@Override
	@Transactional(readOnly = true)
	public User findByUserName(String userName) {
		return userRepository.findByEmail(userName);
	}

	@Override
	public User createUser(@NotNull Signup signup) {
        final Optional<?> existingUserProfile = userProfileRepository.findByEmail(signup.getEmail());
        if (existingUserProfile.isPresent()) {
            return null;
        } else {
            final String userId = UUID.randomUUID().toString();
            final UserProfile userProfile = new UserProfile(userId, signup.getFirstname() + " " + signup.getLastname(), signup.getFirstname(), signup.getLastname(), signup.getEmail(), null);
            final Profile profile = new Profile(userId, userProfile, null, UserType.NATIVE);
            return userRepository.createUser(profile);
        }
	}

    @Override
    public void createRegistrationToken(User user, String token) {
        final RegistrationToken registrationToken = new RegistrationToken(user, token);
        registrationTokenRepository.save(registrationToken);
    }

    @Override
    public TokenStatus confirmRegistrationToken(String token) {
        final RegistrationToken registrationToken = registrationTokenRepository.findByToken(token);
        if (registrationToken == null) {
            return TokenStatus.INVALID;
        } else {
            final User user = registrationToken.getUser();
            if (registrationToken.getExpirationDate().getTime() - Calendar.getInstance().getTime().getTime() <= 0) {
                return TokenStatus.EXPIRED;
            } else {
                user.setEnabled(true);
                registrationTokenRepository.delete(registrationToken);
                userRepository.save(user);
                return TokenStatus.VALID;
            }
        }
    }

    @Override
    public RegistrationToken regenerateToken(final String token) {
        final RegistrationToken registrationToken = registrationTokenRepository.findByToken(token);
        if (registrationToken == null) {
            return null;
        } else {
            final RegistrationToken newToken = new RegistrationToken(registrationToken.getUser(), UUID.randomUUID().toString());
            registrationToken.setExpirationDate(newToken.getExpirationDate());
            registrationToken.setToken(newToken.getToken());
            registrationTokenRepository.save(registrationToken);
            return registrationToken;
        }
    }
}
