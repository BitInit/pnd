package site.bitinit.pnd.web.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.bitinit.pnd.web.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author john
 * @date 2020-01-10
 */
@Slf4j
@Component
public class DatabaseInitialization {

    private final DataSource dataSource;
    private final PndProperties pndProperties;

    @Autowired
    public DatabaseInitialization(DataSource dataSource, PndProperties pndProperties) {
        this.dataSource = dataSource;
        this.pndProperties = pndProperties;
    }

    @PostConstruct
    public void initialization(){

        if (Objects.isNull(dataSource)){
            throw new RuntimeException("datasource can't be null");
        }

        executeSqlFile(dataSource);
    }

    private void executeSqlFile(DataSource dataSource){
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement()){

            List<String> sqlList = loadSql();
            for (String sql: sqlList) {
                statement.execute(sql);
            }
        } catch (Exception e) {
            // 表已经存在
            log.warn(e.getMessage());
        }
    }

    private static final String SQL_FILE_NAME = "META-INFO" + File.separator + "schema.sql";
    private static final String MYSQL_SQL_FILE_NAME = "META-INFO" + File.separator + "schema-mysql.sql";
    private List<String> loadSql() throws Exception {
        List<String> sqlList = new ArrayList<>();
        InputStream inputStream = null;
        try {
            String sqlFileName = SQL_FILE_NAME;
            if (pndProperties.isUseMysql()){
                sqlFileName = MYSQL_SQL_FILE_NAME;
            }

            ClassLoader classLoader = getClass().getClassLoader();
            URL url = classLoader.getResource(sqlFileName);
            inputStream = url.openStream();

            log.info("load sql: {}", url.getPath());

            StringBuilder sqlSb = new StringBuilder();
            byte[] buff = new byte[1024];
            int byteRead = 0;
            while ((byteRead = inputStream.read(buff)) != -1) {
                sqlSb.append(new String(buff, 0, byteRead, StandardCharsets.UTF_8));
            }

            String[] sqlArr = sqlSb.toString().split(";");
            for (String s : sqlArr) {
                String sql = s.replaceAll("--.*", "").trim();
                sql = sql.replaceAll("/\\*.*", "").trim();
                if (StringUtils.hasLength(sql)) {
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
