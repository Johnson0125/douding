package com.douding.douding.config;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author zsit
 */
@Configuration
public class EbeanConfig {



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
        config.setDefaultServer(true);

        return DatabaseFactory.create(config);
    }
}
