package site.bitinit.pnd.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import site.bitinit.pnd.common.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author: john
 * @date: 2019/3/25
 */
@Component
public class DatabaseInitialization {

    private Logger logger = LoggerFactory.getLogger("system");

    @Autowired
    private Properties properties;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initialization(){
        DataSource dataSource = jdbcTemplate.getDataSource();
        if (Objects.isNull(dataSource)){
            throw new RuntimeException("datasource can't be null");
        }

        executeSqlFile(dataSource);
    }

    private void executeSqlFile(DataSource dataSource){
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement()){
            
            List<String> sqlList = loadSql();
            for (String sql :
                    sqlList) {
                statement.execute(sql);
            }
        } catch (Exception e) {
           // PASS 表已经存在
        }
    }

    private static final String SQL_FILE_NAME = "schema.sql";
    private static final String MYSQL_SQL_FILE_NAME = "schema-mysql.sql";
    private List<String> loadSql() throws Exception {
        List<String> sqlList = new ArrayList<>();
        InputStream inputStream = null;
        try {
            String sqlFileName = SQL_FILE_NAME;
            if (properties.isUseMysql()){
                sqlFileName = MYSQL_SQL_FILE_NAME;
            }
            if (StringUtils.isBlank(properties.getPndHome())) {
                ClassLoader classLoader = getClass().getClassLoader();
                URL url = classLoader.getResource(sqlFileName);
                inputStream = url.openStream();
            } else {
                File file = new File(
                        properties.getPndHome() + File.separator + "conf" + File.separator + sqlFileName);
                inputStream = new FileInputStream(file);
            }

            StringBuffer sqlSb = new StringBuffer();
            byte[] buff = new byte[1024];
            int byteRead = 0;
            while ((byteRead = inputStream.read(buff)) != -1) {
                sqlSb.append(new String(buff, 0, byteRead, "UTF-8"));
            }

            String[] sqlArr = sqlSb.toString().split(";");
            for (int i = 0; i < sqlArr.length; i++) {
                String sql = sqlArr[i].replaceAll("--.*", "").trim();
                if (StringUtils.isNotEmpty(sql)) {
                    sqlList.add(sql);
                }
            }
        } finally {
            if (inputStream != null){
                inputStream.close();
            }
        }
        return sqlList;
    }
}
