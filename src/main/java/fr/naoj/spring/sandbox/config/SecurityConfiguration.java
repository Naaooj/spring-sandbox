package fr.naoj.spring.sandbox.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import fr.naoj.spring.sandbox.social.SocialUserDetailServiceImpl;

/**
 * @author Johann Bernez
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	@Qualifier("sandboxUserDetailsService")
	private UserDetailsService userDetailsService;
	
	@Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
        auth.authenticationProvider(authProvider());
    }
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/index.html", "/login.html");
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
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
            .apply(new SpringSocialConfigurer()
                .postLoginUrl("/")
                .defaultFailureUrl("/#/login")
                .alwaysUsePostLoginUrl(true))
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(new SimpleUrlLogoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
            .rememberMe()
            	.rememberMeParameter("remember-me")
            	.tokenValiditySeconds(86400)
            	.tokenRepository(persistentTokenRepository());
    }
    
    @Bean
    public SocialUserDetailsService socialUserDetailsService() {
    	return new SocialUserDetailServiceImpl(userDetailsService());
    }
    
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
    	JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
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
}
