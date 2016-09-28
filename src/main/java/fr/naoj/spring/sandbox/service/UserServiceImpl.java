package fr.naoj.spring.sandbox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.naoj.spring.sandbox.persistence.UsersRepository;
import fr.naoj.spring.sandbox.persistence.entity.Users;

/**
 * @author Johann Bernez
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UsersRepository userDao;
	
	@Override
	public Users findByUserName(String userName) {
		return userDao.findByUsername(userName);
	}

}
