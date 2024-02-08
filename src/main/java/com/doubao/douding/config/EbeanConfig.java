package com.doubao.douding.config;

import com.doubao.douding.entity.SnowflakeIdGenerator;
import com.doubao.douding.system.CurrentUser;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.spring.txn.SpringJdbcTransactionManager;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Johnson
 */
@Configuration
public class EbeanConfig {

    @Bean
    public Database database(CurrentUser currentUser, DataSource dataSource) {
        DatabaseConfig config = new DatabaseConfig();
        config.loadFromProperties();

        config.setDataSource(dataSource);
        config.setDefaultServer(true);
        config.setCurrentUserProvider(currentUser);
        config.setIdGenerators(List.of(new SnowflakeIdGenerator()));
        config.setExternalTransactionManager(new SpringJdbcTransactionManager());

        return DatabaseFactory.create(config);
    }
}
