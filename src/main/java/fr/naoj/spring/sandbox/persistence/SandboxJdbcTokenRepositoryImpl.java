package fr.naoj.spring.sandbox.persistence;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.sql.ResultSet;
import java.util.Date;

/**
 * @author Johann Bernez
 */
public class SandboxJdbcTokenRepositoryImpl extends JdbcDaoSupport implements PersistentTokenRepository {

    /** The default SQL used by the <tt>getTokenBySeries</tt> query */
    private static final String TOKEN_BY_SERIES_SQL = "select email, series, token, last_used from persistent_logins where series = ?";
    /** The default SQL used by <tt>createNewToken</tt> */
    private static final String INSERT_TOKEN_SQL = "insert into persistent_logins (email, series, token, last_used) values(?,?,?,?)";
    /** The default SQL used by <tt>updateToken</tt> */
    private static final String UPDATE_TOKEN_SQL = "update persistent_logins set token = ?, last_used = ? where series = ?";
    /** The default SQL used by <tt>removeUserTokens</tt> */
    private static final String REMOVE_USER_TOKENS_SQL = "delete from persistent_logins where email = ?";

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        getJdbcTemplate().update(INSERT_TOKEN_SQL, token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        getJdbcTemplate().update(UPDATE_TOKEN_SQL, tokenValue, lastUsed, series);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        try {
            return getJdbcTemplate()
                    .queryForObject(TOKEN_BY_SERIES_SQL,
                        (ResultSet rs, int rowNum) -> new PersistentRememberMeToken(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4)),
                        seriesId);
        }
        catch (EmptyResultDataAccessException zeroResults) {
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("Querying token for series '%s' returned no results.", seriesId), zeroResults);
            }
        } catch (IncorrectResultSizeDataAccessException moreThanOne) {
            logger.error(String.format("Querying token for series '%s' returned more than one value. Series should be unique", seriesId));
        } catch (DataAccessException e) {
            logger.error(String.format("Failed to load token for series %s", seriesId), e);
        }

        return null;
    }

    @Override
    public void removeUserTokens(String username) {
        getJdbcTemplate().update(REMOVE_USER_TOKENS_SQL, username);
    }
}
