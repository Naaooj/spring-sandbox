package fr.naoj.spring.sandbox.persistence;

import fr.naoj.spring.sandbox.persistence.entity.UserProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Johann Bernez
 */
public interface UserProfileRepository extends CrudRepository<UserProfile, String> {
    /**
     * Searches a {@link UserProfile} by an email.
     * @param email
     * @return the UserProfile or an {@link Optional}
     */
    Optional<UserProfile> findByEmail(String email);
}
