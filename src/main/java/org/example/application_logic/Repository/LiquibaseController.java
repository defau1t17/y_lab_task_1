package org.example.application_logic.Repository;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;


public class LiquibaseController {

    /**
     * Метод для создания всех схем и таблиц
     */

    public void createSchemasAndTables() {

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/y_lab_db", "y_lab", "y_lab");
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
