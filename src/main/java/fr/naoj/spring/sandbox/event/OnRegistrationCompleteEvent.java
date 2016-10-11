package fr.naoj.spring.sandbox.event;

import fr.naoj.spring.sandbox.persistence.entity.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * @author Johann Bernez
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final User user;
    private final String url;
    private final Locale locale;

    public OnRegistrationCompleteEvent(final User user, final String url, final Locale locale) {
        super(user);
        this.user = user;
        this.url = url;
        this.locale = locale;
    }

    public User getUser() {
        return user;
    }

    public String getUrl() {
        return url;
    }

    public Locale getLocale() {
        return locale;
    }
}
