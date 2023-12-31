package org.example.application_logic.Service.GlobalService;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.example.application_logic.Connector.DBConnectorConfig;

import java.sql.Connection;


public class LiquibaseService {

    /**
     * Функция по созданию схем и таблиц
     */

    public void createMigrationSchema() {

        try {
            Connection connection = DBConnectorConfig.getConnection();
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase createMigrationSchemaLiquibase = new Liquibase("db/changelog/create-migration-schema.xml", new ClassLoaderResourceAccessor(), database);
            createMigrationSchemaLiquibase.update("");

            createMigrationSchemaLiquibase.close();
            connection.close();
        } catch (Exception e) {

        }

    }

    public void createSchemasAndTables() {
        try {
            Connection connection = DBConnectorConfig.getConnection();
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            database.setLiquibaseSchemaName("migrations");
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update("");

            liquibase.close();
            connection.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
