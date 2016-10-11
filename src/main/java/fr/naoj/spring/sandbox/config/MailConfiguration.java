package fr.naoj.spring.sandbox.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author Johann Bernez
 */
@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfiguration {

    private final Logger LOG = LoggerFactory.getLogger(WebConfiguration.class);

    @Autowired
    private Environment environment;

    @Bean
    public JavaMailSender javaMailSenderImpl() {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        try {
            mailSender.setHost(environment.getRequiredProperty("smtp.host"));
            mailSender.setPort(environment.getRequiredProperty("smtp.port", Integer.class));
            mailSender.setProtocol(environment.getRequiredProperty("smtp.protocol"));
            mailSender.setUsername(environment.getRequiredProperty("smtp.username"));
            mailSender.setPassword(environment.getRequiredProperty("smtp.password"));
        } catch (IllegalStateException ex) {
            LOG.error("Could not resolve mail.properties");
            throw ex;
        }
        final Properties javaMailProps = new Properties();
        javaMailProps.put("mail.smtp.auth", true);
        javaMailProps.put("mail.smtp.starttls.enable", true);
        mailSender.setJavaMailProperties(javaMailProps);
        return mailSender;
    }
}
