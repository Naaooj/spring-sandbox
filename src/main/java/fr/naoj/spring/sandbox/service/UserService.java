package fr.naoj.spring.sandbox.service;

import fr.naoj.spring.sandbox.model.SignupModel;
import fr.naoj.spring.sandbox.model.TokenStatus;
import fr.naoj.spring.sandbox.persistence.entity.User;

import javax.validation.constraints.NotNull;

/**
 * @author Johann Bernez
 */
public interface UserService {

	User findByUserName(String userName);

	/**
	 * Creates a new user based on the given {@link SignupModel}.
	 * @param signup the information required to create a new user
	 * @return the newly created used
	 */
	User createUser(@NotNull SignupModel signup);

    /**
     * Creates a new registration token for the given User
     * @param user
     * @param token
     */
    void createRegistrationToken(User user, String token);

	TokenStatus confirmRegistrationToken(String token);

    User regenerateToken(String token);
}
