package fr.naoj.spring.sandbox.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

/**
 * @author Johann Bernez
 */
@Configuration
@EnableSocial
@PropertySource("classpath:application.properties")
public class SocialConfiguration implements SocialConfigurer {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private ConnectionSignUp connectionSignupService;
	
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfc, Environment env) {
		GoogleConnectionFactory gcf = new GoogleConnectionFactory(env.getProperty("spring.social.google.clientId"), env.getProperty("spring.social.google.clientSecret"));
        gcf.setScope("email");
        cfc.addConnectionFactory(gcf);
        
        TwitterConnectionFactory tcf = new TwitterConnectionFactory(env.getProperty("spring.social.twitter.clientId"), env.getProperty("spring.social.twitter.clientSecret"));
        cfc.addConnectionFactory(tcf);
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator cfl) {
		JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, cfl, Encryptors.noOpText());
		repository.setConnectionSignUp(connectionSignupService);
		return repository;
	}
	
	@Bean
	@Scope(value="request", proxyMode=ScopedProxyMode.INTERFACES)
	public Google google(ConnectionRepository repository) {
		Connection<Google> connection = repository.findPrimaryConnection(Google.class);
		return connection != null ? connection.getApi() : null;
	}

}