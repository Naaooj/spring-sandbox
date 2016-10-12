package fr.naoj.spring.sandbox.event.listener;

import fr.naoj.spring.sandbox.event.OnRegistrationCompleteEvent;
import fr.naoj.spring.sandbox.persistence.entity.User;
import fr.naoj.spring.sandbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Johann Bernez
 */
@Component
public class RegistrationCompleteListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Environment environment;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        final User user = onRegistrationCompleteEvent.getUser();
        final String token = UUID.randomUUID().toString();
        userService.createRegistrationToker(user, token);

        final SimpleMailMessage mailMessage = createMessage(onRegistrationCompleteEvent, user.getUserProfile().getEmail(), token);
        mailSender.send(mailMessage);
    }

    private SimpleMailMessage createMessage(final OnRegistrationCompleteEvent event, final String email, final String token) {
        final String recipientAddress = email;
        final String subject = messageSource.getMessage("registration.subject", null, event.getLocale());
        final String confirmationUrl = event.getUrl() + "/confirmRegistration?token=" + token;
        final String message = messageSource.getMessage("registration.succeeded", null, event.getLocale());
        final SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(recipientAddress);
        emailMessage.setSubject(subject);
        emailMessage.setText(message + " \r\n" + confirmationUrl);
        emailMessage.setFrom(environment.getProperty("support.email"));
        return emailMessage;
    }
}
