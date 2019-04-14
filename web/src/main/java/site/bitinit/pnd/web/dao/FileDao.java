package site.bitinit.pnd.web.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import site.bitinit.pnd.common.util.CommonUtils;
import site.bitinit.pnd.web.config.SystemConstants;
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
        return daoUtils.queryForObject(sql, FILE_ROW_MAPPER, id);
    }

    public List<PndFile> findByParentId(long parentId){
        String sql = "select" + FILE_ALL_FIELDS + "from" + FILE_TABLE_NAME + "where parent_id = ?";
        return daoUtils.queryForList(sql, FILE_ROW_MAPPER, parentId);
    }

    public List<PndFile> findByParentIdSortByGmtModified(long parentId){
        String sql = "select" + FILE_ALL_FIELDS + "from" + FILE_TABLE_NAME + "where parent_id = ? order by gmt_modified";
        return daoUtils.queryForList(sql, FILE_ROW_MAPPER, parentId);
    }

    public List<PndFile> findSubfolderByParentId(long id){
        String sql = "select" + FILE_ALL_FIELDS + "from" + FILE_TABLE_NAME +
                "where parent_id = ? and type = ?";
        logger.debug("[file query] id-{} {}", id, sql);
        return daoUtils.queryForList(sql, FILE_ROW_MAPPER, id, SystemConstants.FileType.FOLDER.toString());
    }

    public void createFolder(long parentId, String name){
        String sql = "insert into " + FILE_TABLE_NAME + "(name, parent_id, type, gmt_create, gmt_modified) values "
                    + "(?, ?, ?, ?, ?)";
        String datetime = CommonUtils.formatDate();
        logger.debug("[db insert] parent_id-{} folder_name-{} {}", parentId, name, sql);
        jdbcTemplate.update(sql, name, parentId, SystemConstants.FileType.FOLDER.toString(),
                datetime, datetime);
    }

    public void save(PndFile file){
        String sql = "insert into " + FILE_TABLE_NAME + "(name, parent_id, type, gmt_create, gmt_modified, resource_id) values "
                + "(?, ?, ?, ?, ?, ?)";
        String datetime = CommonUtils.formatDate();
        logger.debug("[db insert] {}", sql);
        jdbcTemplate.update(sql, file.getName(), file.getParentId(), file.getType(),
                datetime, datetime, file.getResourceId());
    }

    public void renameFile(long id, String fileName){
        String sql = "update " + FILE_TABLE_NAME + "set name = ?, gmt_modified = ? where id = ?";
        logger.debug("[db update] id-{} file_name-{} {}", id, fileName, sql);
        jdbcTemplate.update(sql, fileName, CommonUtils.formatDate(), id);
    }

    public void deleteFile(long id){
        String sql = "delete from " + FILE_TABLE_NAME + "where id = ?";
        logger.debug("[db delete] id-{} {}", id, sql);
        jdbcTemplate.update(sql, id);
    }

    public void moveFile(long id, long targetId){
        String sql = "update " + FILE_TABLE_NAME + " set parent_id = ?, gmt_modified = ? where id = ?";
        logger.debug("[file update] id-{} targetId-{} {}", id, targetId, sql);
        jdbcTemplate.update(sql, targetId, CommonUtils.formatDate(), id);
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
