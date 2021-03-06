package fr.naoj.spring.sandbox.config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import fr.naoj.spring.sandbox.persistence.SandboxJdbcTokenRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import fr.naoj.spring.sandbox.social.SocialUserDetailServiceImpl;

import java.io.IOException;

/**
 * @author Johann Bernez
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String REMEMBER_ME_KEY = "spring.security.rememberMe.key";
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private Environment environment;
	
	@Autowired
	@Qualifier("sandboxUserDetailsService")
	private UserDetailsService userDetailsService;
	
	@Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
        	.dataSource(dataSource)
        	.usersByUsernameQuery("select email, password, enabled from users where email=?")
        	.authoritiesByUsernameQuery("select email, authority from authorities where email=?");
        auth.authenticationProvider(authProvider());
    }
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/index.html", "/login.html", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
            .authorizeRequests()
                .antMatchers("/index", "/auth/**", "/signin/**", "/signup/**").permitAll()
                .antMatchers("/**").authenticated()
                .and()
            .formLogin()
            	.loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
                .and()
            .apply(new SpringSocialConfigurer()
                .postLoginUrl("/")
                .defaultFailureUrl("/login")
                .alwaysUsePostLoginUrl(true))
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new SimpleUrlLogoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
            .rememberMe()
            	.key(environment.getProperty(REMEMBER_ME_KEY))
            	.useSecureCookie(false)
            	.rememberMeParameter("remember-me")
            	.tokenValiditySeconds(86400)
            	.rememberMeServices(rememberMeServices())
            	.tokenRepository(persistentTokenRepository());
    }
    
    @Bean
    public SocialUserDetailsService socialUserDetailsService() {
    	return new SocialUserDetailServiceImpl(userDetailsService);
    }
    
    @Bean
    public RememberMeServices rememberMeServices() {
    	RememberMeServices rememberMeService = new PersistentTokenBasedRememberMeServices(environment.getProperty(REMEMBER_ME_KEY), userDetailsService, persistentTokenRepository()) {
    		@Override
    		protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
    			String uri = request.getRequestURI();
    			if (uri.matches("/auth/.*")) {
    				return true;
    			}
    			return super.rememberMeRequested(request, parameter);
    		}
    	};
    	return rememberMeService;
    }
    
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
    	SandboxJdbcTokenRepositoryImpl jdbcTokenRepository = new SandboxJdbcTokenRepositoryImpl();
    	jdbcTokenRepository.setDataSource(dataSource);
    	return jdbcTokenRepository;
    }
    
    @Bean
    public AuthenticationProvider authProvider() {
    	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    	authProvider.setUserDetailsService(userDetailsService);
    	authProvider.setPasswordEncoder(passwordEncoder());
    	return authProvider;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public AuthenticationFailureHandler failureHandler() {
		return new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
				System.out.println("error");
			}
		};
	}
}
