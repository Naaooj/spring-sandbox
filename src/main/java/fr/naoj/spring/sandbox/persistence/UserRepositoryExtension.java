package fr.naoj.spring.sandbox.persistence;

import fr.naoj.spring.sandbox.model.Profile;
import fr.naoj.spring.sandbox.persistence.entity.User;

/**
 * @author Johann Bernez
 */
public interface UserRepositoryExtension {

	User createUser(Profile profile);
}
