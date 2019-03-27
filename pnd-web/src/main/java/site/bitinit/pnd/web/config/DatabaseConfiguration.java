package site.bitinit.pnd.web.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import site.bitinit.pnd.common.util.StringUtils;

import javax.sql.DataSource;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author: john
 * @date: 2019/3/25
 */
@Configuration
public class DatabaseConfiguration {

    @Autowired
    private Properties properties;

    @Bean
    public DataSource buildDataSource(){
        if (properties.isUseMysql()){
            return buildMysqlDataSource();
        } else {
            return buildEmbedDBDataSource();
        }
    }

    @Bean
    public JdbcTemplate buildJdbcTemplate(DataSource dataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        jdbcTemplate.setMaxRows(50000);
        jdbcTemplate.setQueryTimeout(5000);
        return jdbcTemplate;
    }

    @Bean
    public PlatformTransactionManager buildTransactionManager(DataSource dataSource){
        DataSourceTransactionManager tm = new DataSourceTransactionManager();
        tm.setDataSource(dataSource);
        return tm;
    }

    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private DataSource buildMysqlDataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(MYSQL_DRIVER);
        dataSource.setUrl(properties.getDbURL());
        dataSource.setUsername(properties.getDbUsername());
        dataSource.setPassword(properties.getDbPassword());
        dataSource.setInitialSize(properties.getDbInitialSize());
        dataSource.setMaxActive(properties.getDbMaxActive());
        dataSource.setMaxIdle(properties.getDbMaxIdle());

        dataSource.setMaxWait(3000L);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setTimeBetweenEvictionRunsMillis(TimeUnit.MINUTES.toMillis(10L));
        dataSource.setTestWhileIdle(true);
        dataSource.setValidationQuery("SELECT 1 FROM dual");
        return dataSource;
    }

    private DataSource buildEmbedDBDataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(properties.getEmbedDBDriver());
        String url = "jdbc:derby:" + properties.getDataDir() + File.separator + properties.getEmbedDBBaseDir() + ";create=true";
        if (StringUtils.isBlank(properties.getDataDir())){
            url = "jdbc:derby:" + properties.getPndHome() + File.separator + "data" + File.separator + properties.getEmbedDBBaseDir() + ";create=true";
        }
        dataSource.setUrl(url);
        dataSource.setUsername(properties.getEmbedDBUsername());
        dataSource.setPassword(properties.getEmbedDBPassword());
        dataSource.setInitialSize(20);
        dataSource.setMaxActive(30);
        dataSource.setMaxIdle(50);
        dataSource.setMaxWait(10000L);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setTimeBetweenEvictionRunsMillis(TimeUnit.MINUTES
                .toMillis(10L));
        dataSource.setTestWhileIdle(true);
        return dataSource;
    }
}
