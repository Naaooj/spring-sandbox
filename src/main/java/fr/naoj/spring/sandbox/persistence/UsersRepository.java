package fr.naoj.spring.sandbox.persistence;

import org.springframework.data.repository.Repository;

import fr.naoj.spring.sandbox.persistence.entity.Users;

/**
 * @author Johann Bernez
 */
public interface UsersRepository extends Repository<Users, String> {
	
	Users findByUsername(String username);
}
