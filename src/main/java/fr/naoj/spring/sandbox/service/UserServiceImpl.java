package fr.naoj.spring.sandbox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.naoj.spring.sandbox.persistence.UserRepository;
import fr.naoj.spring.sandbox.persistence.entity.User;

/**
 * @author Johann Bernez
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userDao;
	
	@Override
	public User findByUserName(String userName) {
		return userDao.findByUsername(userName);
	}

}
