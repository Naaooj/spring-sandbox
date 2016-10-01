package fr.naoj.spring.sandbox.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.naoj.spring.sandbox.persistence.entity.UserProfile;
import fr.naoj.spring.sandbox.persistence.entity.Users;
import fr.naoj.spring.sandbox.service.UserService;

/**
 * @author Johann Bernez
 */
@Service("sandboxUserDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	@Override
	@Transactional(readOnly = false)
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Users users = userService.findByUserName(userName);
		if (users == null) {
			throw new UsernameNotFoundException("Username not found");
		}
		UserProfile userProfile = users.getUserProfile();
		if (userProfile != null) {
			return new SocialUser(users.getUsername(), users.getPassword(), getGrantedAuthorities());
		} else {
			return new User(users.getUsername(), users.getPassword(), getGrantedAuthorities());
		}
	}

	private List<GrantedAuthority> getGrantedAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		return authorities;
	}
}
