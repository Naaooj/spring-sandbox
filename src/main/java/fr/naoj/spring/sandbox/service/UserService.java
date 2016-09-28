package fr.naoj.spring.sandbox.service;

import fr.naoj.spring.sandbox.persistence.entity.Users;

/**
 * @author Johann Bernez
 */
public interface UserService {

	Users findByUserName(String userName);
}
