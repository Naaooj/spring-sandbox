package fr.naoj.spring.sandbox.service;

import fr.naoj.spring.sandbox.model.Signup;
import fr.naoj.spring.sandbox.persistence.entity.User;

import javax.validation.constraints.NotNull;

/**
 * @author Johann Bernez
 */
public interface UserService {

	User findByUserName(String userName);

	/**
	 * Creates a new user based on the given {@link Signup}.
	 * @param signup the information required to create a new user
	 * @return the newly created used
	 */
	User createUser(@NotNull Signup signup);

    /**
     * Creates a new registration token for the given User
     * @param user
     * @param token
     */
    void createRegistrationToker(User user, String token);
}
