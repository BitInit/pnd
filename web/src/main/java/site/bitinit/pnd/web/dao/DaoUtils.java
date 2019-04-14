package site.bitinit.pnd.web.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author: john
 * @date: 2019/4/4
 */
@Component
public class DaoUtils {

    private static final Logger logger = LoggerFactory.getLogger(DaoUtils.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public <T> T queryForObject(String sql, RowMapper rowMapper, Object... objects){
        try {
            return (T) jdbcTemplate.queryForObject(sql, objects, rowMapper);
        } catch (CannotGetJdbcConnectionException e){
            logger.error("[db-error] ", e);
            throw e;
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public <T> List<T> queryForList(String sql, RowMapper rowMapper, Object... objects){
        try {
            return jdbcTemplate.query(sql, objects, rowMapper);
        } catch (CannotGetJdbcConnectionException e){
            logger.error("[db-error] ", e);
            throw e;
        } catch (EmptyResultDataAccessException e){
            return Collections.emptyList();
        }
    }
}
