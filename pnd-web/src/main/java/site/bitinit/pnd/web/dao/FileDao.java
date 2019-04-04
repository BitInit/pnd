package site.bitinit.pnd.web.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import site.bitinit.pnd.web.model.PndFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: john
 * @date: 2019/4/2
 */
@Component
public class FileDao {
    private static final Logger logger = LoggerFactory.getLogger(FileDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DaoUtils daoUtils;
    public PndFile findById(long id){
        String sql = "select" + FILE_ALL_FIELDS + "from" + FILE_TABLE_NAME + "where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, FILE_ROW_MAPPER);
        } catch (CannotGetJdbcConnectionException e){
            logger.error("[db-error] ", e);
            throw e;
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<PndFile> findByParentId(long parentId){
        String sql = "select" + FILE_ALL_FIELDS + "from" + FILE_TABLE_NAME + "where parent_id = ?";
        return daoUtils.queryForList(sql, FILE_ROW_MAPPER, parentId);
    }

    public static final String FILE_TABLE_NAME = " pnd_file ";
    private static final String FILE_ALL_FIELDS = " id, name, parent_id, type, gmt_create, gmt_modified, resource_id ";
    private static final FileRowMapper FILE_ROW_MAPPER = new FileRowMapper();

    static class FileRowMapper implements RowMapper<PndFile> {

        @Override
        public PndFile mapRow(ResultSet rs, int rowNum) throws SQLException {
            PndFile f = new PndFile();
            f.setId(rs.getLong("id"));
            f.setName(rs.getString("name"));
            f.setParentId(rs.getLong("parent_id"));
            f.setType(rs.getString("type"));
            f.setGmtCreate(rs.getTimestamp("gmt_create").getTime());
            f.setGmtModified(rs.getTimestamp("gmt_modified").getTime());
            f.setResourceId(rs.getLong("resource_id"));
            return f;
        }
    }
}
