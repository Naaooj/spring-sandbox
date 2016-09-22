package fr.naoj.spring.sandbox.bootstrap;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import fr.naoj.spring.sandbox.config.PersistenceConfiguration;
import fr.naoj.spring.sandbox.config.SecurityConfiguration;
import fr.naoj.spring.sandbox.config.SocialConfiguration;
import fr.naoj.spring.sandbox.config.WebConfiguration;

/**
 * @author Johann Bernez
 */
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[]{WebConfiguration.class, PersistenceConfiguration.class, SecurityConfiguration.class, SocialConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

}
