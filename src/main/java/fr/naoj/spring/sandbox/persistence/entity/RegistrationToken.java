package fr.naoj.spring.sandbox.persistence.entity;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Johann Bernez
 */
@Entity
@Table(name = "registration_token")
public class RegistrationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registration_token_seq")
    @SequenceGenerator(name = "registration_token_seq", sequenceName = "registration_token_id_seq")
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "username", foreignKey = @ForeignKey(name = "fk_username"))
    private User user;

    public RegistrationToken() {
        super();
    }

    public RegistrationToken(final User user, final String token) {
        this.user = user;
        this.token = token;
        this.expirationDate = new DateTime(Calendar.getInstance()).plusDays(1).toDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
