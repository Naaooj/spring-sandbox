package fr.naoj.spring.sandbox.persistence;

import org.springframework.data.repository.CrudRepository;

import fr.naoj.spring.sandbox.persistence.entity.Users;

/**
 * @author Johann Bernez
 */
public interface UsersRepository extends CrudRepository<Users, String>, UsersRepositoryExtension {
	
	Users findByUsername(String username);
}
