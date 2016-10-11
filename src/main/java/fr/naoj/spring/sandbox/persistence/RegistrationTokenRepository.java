package fr.naoj.spring.sandbox.persistence;

import fr.naoj.spring.sandbox.persistence.entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Johann Bernez
 */
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {
}
