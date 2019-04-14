package site.bitinit.pnd.web.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import site.bitinit.pnd.common.util.CommonUtils;
import site.bitinit.pnd.web.model.PndResource;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: john
 * @date: 2019/4/4
 */
@Component
public class ResourceDao {

    private static final Logger logger = LoggerFactory.getLogger(ResourceDao.class);

    @Autowired
    private DaoUtils daoUtils;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PndResource findById(long id){
        String sql = "select " + RESOURCE_ALL_FIELDS + " from " + RESOURCE_TABLE_NAME +
                " where id = ?";
        logger.debug("[resource query] id-{} {}", id, sql);
        return daoUtils.queryForObject(sql, RESOURCE_ROW_MAPPER, id);
    }

    public int updateIndex(long id, long expected, long val){
        String sql = "update " + RESOURCE_TABLE_NAME + " set link = ?, gmt_modified = ? " +
                " where id = ? and link = ?";
        return jdbcTemplate.update(sql, val, CommonUtils.formatDate(), id, expected);
    }

    public static final String RESOURCE_TABLE_NAME = " pnd_resource ";
    private static final String RESOURCE_ALL_FIELDS = " id, size, path, uuid, gmt_create, gmt_modified, status, crc, link ";
    private static final ResourceRowMapper RESOURCE_ROW_MAPPER = new ResourceRowMapper();
    static class ResourceRowMapper implements RowMapper<PndResource> {

        @Override
        public PndResource mapRow(ResultSet rs, int rowNum) throws SQLException {
            PndResource resource = new PndResource();
            resource.setId(rs.getLong("id"));
            resource.setSize(rs.getLong("size"));
            resource.setPath(rs.getString("path"));
            resource.setUuid(rs.getString("uuid"));
            resource.setGmtCreate(rs.getTimestamp("gmt_create").getTime());
            resource.setGmtModified(rs.getTimestamp("gmt_modified").getTime());
            resource.setStatus(rs.getString("status"));
            resource.setCrc(rs.getString("crc"));
            resource.setLink(rs.getInt("link"));
            return resource;
        }
    }
}
