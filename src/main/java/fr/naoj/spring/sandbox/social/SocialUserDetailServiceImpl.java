package fr.naoj.spring.sandbox.social;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * @author Johann Bernez
 */
public class SocialUserDetailServiceImpl implements SocialUserDetailsService {

	private final UserDetailsService userDetailsService;
	
	public SocialUserDetailServiceImpl(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	public SocialUserDetails loadUserByUserId(String userName) throws UsernameNotFoundException {
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
		return new SocialUser(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
	}
}
