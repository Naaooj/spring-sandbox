package fr.naoj.spring.sandbox.persistence;

import fr.naoj.spring.sandbox.model.Profile;

/**
 * @author Johann Bernez
 */
public interface UserRepository {

	void createUser(Profile profile);
}
