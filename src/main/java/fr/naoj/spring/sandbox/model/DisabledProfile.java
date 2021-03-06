package fr.naoj.spring.sandbox.model;

import org.springframework.social.connect.UserProfile;

/**
 * @author Johann Bernez
 */
public final class DisabledProfile extends AbstractProfile {

    public DisabledProfile(String email, UserProfile userProfile, String imageUrl, UserType socialType) {
        super(email, userProfile, imageUrl, socialType);
    }

    public final boolean isEnabled() {
        return false;
    }
}
