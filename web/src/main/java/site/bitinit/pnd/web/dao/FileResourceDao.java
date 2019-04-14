package site.bitinit.pnd.web.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import site.bitinit.pnd.web.controller.dto.FileDetailDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @author: john
 * @date: 2019/4/3
 */
@Component
public class FileResourceDao {

    private static final Logger logger = LoggerFactory.getLogger(FileResourceDao.class);
    @Autowired
    private DaoUtils daoUtils;

    public List<FileDetailDto> findFileDetailByParentId(long parentId){
        String sql = "select f.id as f_id, f.name as f_name, f.parent_id as f_parent_id, f.type as f_type,"
                +"f.gmt_modified as f_gmt_modified, f.gmt_create as f_gmt_create, f.resource_id as f_resource_id,"
                +"r.size as r_size  from" + FileDao.FILE_TABLE_NAME
                + " as f left join " + ResourceDao.RESOURCE_TABLE_NAME
                + " as r on f.resource_id = r.id where f.parent_id = ?";

        logger.debug("[db query] parent_id-{} {}", parentId, sql);
        return daoUtils.queryForList(sql, FILE_RESOURCE_ROW_MAPPER, parentId);
    }

    private static final FileResourceRowMapper FILE_RESOURCE_ROW_MAPPER = new FileResourceRowMapper();
    static class FileResourceRowMapper implements RowMapper<FileDetailDto>{

        @Override
        public FileDetailDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            FileDetailDto fd = new FileDetailDto();
            fd.setId(rs.getLong("f_id"));
            fd.setName(rs.getString("f_name"));
            fd.setType(rs.getString("f_type"));
            fd.setParentId(rs.getLong("f_parent_id"));
            fd.setResourceId(rs.getLong("f_resource_id"));
            fd.setSize(rs.getInt("r_size"));
            fd.setGmtCreate(rs.getTimestamp("f_gmt_create").getTime());
            fd.setGmtModified(rs.getTimestamp("f_gmt_modified").getTime());
            return fd;
        }
    }
}
