package site.bitinit.pnd.web.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import site.bitinit.pnd.web.entity.File;
import site.bitinit.pnd.web.entity.Resource;
import site.bitinit.pnd.web.entity.ResourceChunk;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

/**
 * @author john
 * @date 2020-01-08
 */
@Configuration
public class Config {

    private final PndProperties pndProperties;

    @Autowired
    public Config(PndProperties pndProperties) {
        this.pndProperties = pndProperties;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse(pndProperties.getMaxFileUploadSize()));
        factory.setMaxRequestSize(DataSize.parse(pndProperties.getMaxRequestSize()));
        return factory.createMultipartConfig();
    }

    /**
     * 自定义mybatis配置
     * @return ConfigurationCustomizer
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return configuration -> {
            configuration.setMapUnderscoreToCamelCase(true);

            // 注册别名，用于生产环境识别类别名
            TypeAliasRegistry typeAliasRegistry = configuration.getTypeAliasRegistry();
            typeAliasRegistry.registerAlias(File.ALIAS, File.class);
            typeAliasRegistry.registerAlias(Resource.ALIAS, Resource.class);
            typeAliasRegistry.registerAlias(ResourceChunk.ALIAS, ResourceChunk.class);
        };
    }

    @Bean
    public DataSource buildDataSource(){
        if (pndProperties.isUseMysql()){
            return buildMysqlDatasource();
        } else {
            return buildEmbedDbDatasource();
        }
    }

    private DataSource buildMysqlDatasource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(pndProperties.getMysqlDriver());
        dataSource.setJdbcUrl(pndProperties.getMysqlUrl());
        dataSource.setUsername(pndProperties.getMysqlUsername());
        dataSource.setPassword(pndProperties.getMysqlPassword());
        return dataSource;
    }

    private DataSource buildEmbedDbDatasource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(pndProperties.getEmbedDbDriver());
        dataSource.setJdbcUrl(pndProperties.getEmbedDbUrl());
        dataSource.setUsername(pndProperties.getEmbedDbUsername());
        dataSource.setPassword(pndProperties.getEmbedDbPassword());
        return dataSource;
    }
}
