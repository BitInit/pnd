package site.bitinit.pnd.web.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import site.bitinit.pnd.common.util.CommonUtils;
import site.bitinit.pnd.web.config.SystemConstants;
import site.bitinit.pnd.web.model.PndResource;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    public PndResource findByFingerPrint(String crc, SystemConstants.ResourceState state){
        String sql = "select " + RESOURCE_ALL_FIELDS + " from " + RESOURCE_TABLE_NAME + " where finger_print = ? and status = ?";
        logger.debug("[resource query] crc-{} {}", crc, sql);
        List<PndResource> resources = daoUtils.queryForList(sql, RESOURCE_ROW_MAPPER, crc, state.name());
        if (!Objects.isNull(resources) && resources.size() >= 1){
            return resources.get(0);
        }
        return null;
     }

     public long save(PndResource resource){
        String sql = "insert into " + RESOURCE_TABLE_NAME + " (size, path, uuid, gmt_create, gmt_modified, status, finger_print) "
                + "values (?, ?, ?, ?, ?, ?, ?)";
         KeyHolder keyHolder = new GeneratedKeyHolder();
         jdbcTemplate.update(new PreparedStatementCreator() {
             @Override
             public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                 PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                 ps.setLong(1, resource.getSize());
                 ps.setString(2, resource.getPath());
                 ps.setString(3, resource.getUuid());
                 ps.setString(4, CommonUtils.formatDate(new Date(resource.getGmtCreate())));
                 ps.setString(5, CommonUtils.formatDate(new Date(resource.getGmtModified())));
                 ps.setString(6, resource.getStatus());
                 ps.setString(7, resource.getFingerPrint());
                 return ps;
             }
         }, keyHolder);

        return keyHolder.getKey().longValue();
     }

     public int updateState(long id, SystemConstants.ResourceState resourceState){
        String sql = "update " + RESOURCE_TABLE_NAME + " set status = ?, gmt_modified = ?" +
                " where id = ?";
        return jdbcTemplate.update(sql, resourceState.name(), CommonUtils.formatDate(), id);
     }

    public int updateIndex(long id, long expected, long val){
        String sql = "update " + RESOURCE_TABLE_NAME + " set link = ?, gmt_modified = ? " +
                " where id = ? and link = ?";
        return jdbcTemplate.update(sql, val, CommonUtils.formatDate(), id, expected);
    }

    public static final String RESOURCE_TABLE_NAME = " pnd_resource ";
    private static final String RESOURCE_ALL_FIELDS = " id, size, path, uuid, gmt_create, gmt_modified, status, finger_print, link ";
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
            resource.setFingerPrint(rs.getString("finger_print"));
            resource.setLink(rs.getInt("link"));
            return resource;
        }
    }
}
