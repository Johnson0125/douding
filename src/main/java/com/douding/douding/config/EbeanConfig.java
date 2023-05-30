package com.douding.douding.config;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class EbeanConfig {

    @Bean(name = "datasource")
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public Database database(DataSource dataSource){
        DatabaseConfig config = new DatabaseConfig();
//        Properties properties = new Properties();
//        properties.put("ebean.db.ddl.generate", "true");
//        properties.put("ebean.db.ddl.run", "true");
//        properties.put("datasource.db.username", "sa");
//        properties.put("datasource.db.password", "");
//        properties.put("datasource.db.databaseUrl","jdbc:h2:mem:app2";
//        properties.put("datasource.db.databaseDriver", "org.h2.Driver");
//
//        config.loadFromProperties(properties);

        config.setDataSource(dataSource);

        return DatabaseFactory.create(config);
    }
}
