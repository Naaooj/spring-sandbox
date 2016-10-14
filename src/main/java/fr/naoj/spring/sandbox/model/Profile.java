package fr.naoj.spring.sandbox.model;

import org.springframework.social.connect.UserProfile;

/**
 * @author Johann Bernez
 */
public interface Profile {
    boolean isEnabled();

    String getEmail();

    UserProfile getUserProfile();

    String getImageUrl();

    UserType getSocialType();
}
