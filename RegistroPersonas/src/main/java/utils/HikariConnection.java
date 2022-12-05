package utils;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

public class HikariConnection {
    
    @Getter @Setter private String user;
    @Getter @Setter private String password;
    @Getter @Setter private int port;
    @Getter @Setter private String database;
    @Getter @Setter private String host;

    @Getter @Setter private String ssl;

    @Getter private final HikariDataSource DATA_SOURCE = new HikariDataSource();
    private Connection connection;

    @SneakyThrows
    public void configureProperties() {
        this.getDATA_SOURCE().setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");
        this.getDATA_SOURCE().addDataSourceProperty("serverName", this.getHost());
        this.getDATA_SOURCE().addDataSourceProperty("user", this.getUser());
        this.getDATA_SOURCE().addDataSourceProperty("password", this.getPassword());
        this.getDATA_SOURCE().addDataSourceProperty("port", this.getPort());
        this.getDATA_SOURCE().addDataSourceProperty("databaseName", this.getDatabase());
        this.getDATA_SOURCE().addDataSourceProperty("autoReconnect", true);
        this.getDATA_SOURCE().addDataSourceProperty("characterEncoding", "latin1");

        if (this.getSsl() == null) {
            this.getDATA_SOURCE().addDataSourceProperty("useSSL", "false");
        } else {
            this.getDATA_SOURCE().addDataSourceProperty("useSSL", this.getSsl());
        }

        this.getDATA_SOURCE().setMaximumPoolSize(30);
        this.getDATA_SOURCE().setMaxLifetime(100000000);
    }

    public synchronized void connect() throws SQLException {
        this.connection = this.getDATA_SOURCE().getConnection();
    }
    
    public Connection getConnection() throws SQLException {
        return connection != null ? this.connection : null;
    }
    
}
