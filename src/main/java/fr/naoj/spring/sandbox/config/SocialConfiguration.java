package fr.naoj.spring.sandbox.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import fr.naoj.spring.sandbox.social.ConnectionSignUpService;

/**
 * @author Johann Bernez
 */
@Configuration
@EnableSocial
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"fr.naoj.spring.sandbox.social"})
public class SocialConfiguration implements SocialConfigurer {

	@Autowired
	private ConnectionSignUpService signUpService;
	
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfc, Environment env) {
		GoogleConnectionFactory gcf = new GoogleConnectionFactory(
                env.getProperty("spring.social.google.appId"),
                env.getProperty("spring.social.google.appSecret"));
        gcf.setScope("email");
        cfc.addConnectionFactory(gcf);
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator cfl) {
		InMemoryUsersConnectionRepository repository = new InMemoryUsersConnectionRepository(cfl);
		repository.setConnectionSignUp(signUpService);
		return repository;
	}

}
