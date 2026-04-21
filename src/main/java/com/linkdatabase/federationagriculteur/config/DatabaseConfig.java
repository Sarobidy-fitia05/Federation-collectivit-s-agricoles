package com.linkdatabase.federationagriculteur.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {
    private final String jdbcURL = System.getenv("JDBC_URL");
    private final String user = System.getenv("federation");
    private final String password = "123456";

    @Bean
        public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/federation","federation", "123456");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
