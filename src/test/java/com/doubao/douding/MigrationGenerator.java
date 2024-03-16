package com.doubao.douding;

import io.ebean.annotation.Platform;
import io.ebean.dbmigration.DbMigration;
import java.io.IOException;

/**
 * @author zhangs890
 * @Description generate db migration according to Domain
 */
public class MigrationGenerator {

    public static void main(String[] args) throws IOException {

        DbMigration migration = DbMigration.create();
        migration.setPlatform(Platform.MYSQL);
        migration.setVersion("20240316.0.0.1");
        migration.setName("base model add operator");
        migration.setStrictMode(false);

        migration.generateMigration();
    }

}
