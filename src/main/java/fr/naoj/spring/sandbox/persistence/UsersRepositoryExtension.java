package fr.naoj.spring.sandbox.persistence;

import fr.naoj.spring.sandbox.model.Profile;

/**
 * @author Johann Bernez
 */
public interface UsersRepositoryExtension {

	void createUser(Profile profile);
}
