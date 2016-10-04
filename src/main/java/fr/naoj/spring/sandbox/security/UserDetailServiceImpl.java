package fr.naoj.spring.sandbox.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.naoj.spring.sandbox.persistence.entity.User;
import fr.naoj.spring.sandbox.persistence.entity.UserProfile;
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
		User user = userService.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Username not found");
		}
		UserProfile userProfile = user.getUserProfile();
		if (userProfile != null) {
			return new SocialUser(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));
		} else {
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));
		}
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		user.getAuthorities().forEach(auth -> new SimpleGrantedAuthority(auth.getId().getAuthority()));
		return authorities;
	}
}
