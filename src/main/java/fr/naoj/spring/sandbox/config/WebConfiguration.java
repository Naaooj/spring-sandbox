package fr.naoj.spring.sandbox.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

import javax.servlet.ServletContext;
import java.util.Locale;

/**
 * @author Johann Bernez
 */
@Configuration
@EnableWebMvc
@ComponentScan("fr.naoj.spring.sandbox")
public class WebConfiguration extends WebMvcConfigurerAdapter implements ServletContextAware {

	private ServletContext servletContext;
	
	@Bean
	public ViewResolver thymeleafViewResolver() {
		final ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		return resolver;
	}
	
	@Bean
	public ITemplateResolver templateResolver() {
		final ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(this.servletContext);
		resolver.setPrefix("/WEB-INF/template/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		resolver.setCacheable(false);
		return resolver;
	}
	
	@Bean
	public UrlTemplateResolver urlTemplateResolver() {
		return new UrlTemplateResolver();
	}
	
	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addTemplateResolver(templateResolver());
		templateEngine.addTemplateResolver(urlTemplateResolver());
		templateEngine.addDialect(new LayoutDialect());
		return templateEngine;
	}
	
	@Bean
	public MessageSource messageSource() {
		final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("i18n.messages", "i18n.validation");
		return messageSource;
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		final SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(new Locale("en_US"));
		return resolver;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public Validator getValidator() {
		return localValidator();
	}

	@Bean
	public LocalValidatorFactoryBean localValidator() {
		final LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(messageSource());
		return localValidatorFactoryBean;
	}
}
