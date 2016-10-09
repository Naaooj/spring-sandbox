package fr.naoj.spring.sandbox.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Johann Bernez
 */
public class Signup {

    @NotNull
    @NotEmpty(message = "{email.error.empty}")
    @Size(min = 8, max = 64, message = "{email.error.size}")
    private String email;

    @NotNull
    @NotEmpty(message = "{password.error.empty}")
    @Size(min = 8, max = 64, message = "{password.error.size}")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
