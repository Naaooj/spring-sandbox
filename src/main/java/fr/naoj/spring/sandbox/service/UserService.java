package fr.naoj.spring.sandbox.service;

import fr.naoj.spring.sandbox.persistence.entity.User;

/**
 * @author Johann Bernez
 */
public interface UserService {

	User findByUserName(String userName);
}
