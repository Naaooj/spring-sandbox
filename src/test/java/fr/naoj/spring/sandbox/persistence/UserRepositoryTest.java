package fr.naoj.spring.sandbox.persistence;

import fr.naoj.spring.sandbox.model.Profile;
import fr.naoj.spring.sandbox.model.UserType;
import fr.naoj.spring.sandbox.persistence.entity.User;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.social.connect.UserProfile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.DockerComposeContainer;

import javax.sql.DataSource;
import java.io.File;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Johann Bernez
 */
@ContextConfiguration()
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryTest {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepositoryTest.class);

    /** Defines the name of the service, must be the same as in the docker compose file, suffixed with _1 */
    private static final String SERVICE_NAME = "postgres_1";

    /** Defines the port of the service */
    private static final Integer SERVICE_PORT = 5432;

    /**
     * Creates a new generic container for all the method, use @Rule for creating
     * the container for each method
     */
    @ClassRule
    public static DockerComposeContainer dockerEnvironment = new DockerComposeContainer(new File("src/test/resources/test-docker-compose.yml"))
            .withExposedService(SERVICE_NAME, SERVICE_PORT);

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        String ip = dockerEnvironment.getServiceHost(SERVICE_NAME, SERVICE_PORT);
        Integer port = dockerEnvironment.getServicePort(SERVICE_NAME, SERVICE_PORT);
        LOG.info(String.format("Postgres running on [%s] with port [%s]", ip, port));
    }

    @Test
    @Transactional
    public void testRepository() {
        final String userId = UUID.randomUUID().toString();
        final String email = "john.doe@gmail.com";
        UserProfile userProfile = new UserProfile(userId, "John Doe", "John", "Doe", email, userId);
        Profile profile = new Profile(userId, userProfile, null, UserType.GOOGLE);
        userRepository.createUser(profile);

        User user = userRepository.findByUsername(userId);
        assertNotNull(user);

        assertEquals(UserType.GOOGLE, user.getType());
        assertEquals(email, user.getUserProfile().getEmail());
    }

    /**
     * <p>Embedded configuration file used to access to the docker environment
     * in order to define the datasource property.</p>
     *
     * <p>The rest of the configuration is in the {@link RepositoryTestConfiguration} class.</p>
     * @author Johann Bernez
     */
    @Configuration
    @Import(RepositoryTestConfiguration.class)
    static class Config {

        @Autowired
        private Environment env;

        @Bean(name = "dataSource")
        public DataSource dataSource() {
            final DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
            dataSource.setUrl(env.getProperty("jdbc.url") + dockerEnvironment.getServicePort(SERVICE_NAME, SERVICE_PORT) + "/");
            dataSource.setUsername(env.getProperty("jdbc.username"));
            dataSource.setPassword(env.getProperty("jdbc.password"));
            return dataSource;
        }
    }
}
