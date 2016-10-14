package fr.naoj.spring.sandbox.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Johann Bernez
 */
public class SignupModel {

    @NotNull
    @NotEmpty(message = "{email.error.empty}")
    @Size(min = 8, max = 64, message = "{email.error.size}")
    private String email;

    @NotNull
    @NotEmpty(message = "{password.error.empty}")
    @Size(min = 8, max = 64, message = "{password.error.size}")
    private String password;

    @NotNull
    @NotEmpty(message = "{firstname.error.empty}")
    @Size(min = 3, max = 64, message = "{firstname.error.size}")
    private String firstname;

    @NotNull
    @NotEmpty(message = "{lastname.error.empty}")
    @Size(min = 3, max = 64, message = "{lastname.error.size}")
    private String lastname;

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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
