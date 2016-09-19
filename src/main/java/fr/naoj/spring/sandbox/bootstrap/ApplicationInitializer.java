package fr.naoj.spring.sandbox.bootstrap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author Johann Bernez
 */
public class ApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext configurationContext = new AnnotationConfigWebApplicationContext();
		configurationContext.setConfigLocation("fr.naoj.spring.sandbox.config");
		
		ContextLoaderListener loaderListener = new ContextLoaderListener(configurationContext);
		servletContext.addListener(loaderListener);
		
		DispatcherServlet dispatcherServlet = new DispatcherServlet(configurationContext);
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
		dispatcher.addMapping("/");
	}

}
