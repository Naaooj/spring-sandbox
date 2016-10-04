package fr.naoj.spring.sandbox.persistence;

import org.springframework.data.repository.CrudRepository;

import fr.naoj.spring.sandbox.persistence.entity.User;

/**
 * @author Johann Bernez
 */
public interface UserRepository extends CrudRepository<User, String>, UserRepositoryExtension {
	
	User findByUsername(String username);
}
