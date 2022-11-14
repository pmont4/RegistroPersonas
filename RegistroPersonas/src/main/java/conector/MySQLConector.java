package conector;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLConector {
    
    private HikariDataSource hikari;

    private String user;
    private String password;
    private String database;
    private String host;
    private Integer port;
    private String SSL;

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void setSSL(String SSL) {
        this.SSL = SSL;
    }

    public void configure() throws Exception {
        hikari = new HikariDataSource();
        hikari.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", host);
        hikari.addDataSourceProperty("user", user);
        hikari.addDataSourceProperty("password", password);
        hikari.addDataSourceProperty("port", port);
        hikari.addDataSourceProperty("databaseName", database);
        hikari.addDataSourceProperty("autoReconnect", true);
        hikari.addDataSourceProperty("characterEncoding", "latin1");
        hikari.addDataSourceProperty("useSSL", SSL);
        hikari.setMaximumPoolSize(30);
        hikari.setMaxLifetime(10000000);
    }

    private Connection conexion;

    public Connection connect() throws SQLException {
        conexion = hikari.getConnection();
        
        if (checkConnection()) {
            try (PreparedStatement stmt = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS personas (id INT NOT NULL AUTO_INCREMENT, nombre VARCHAR(50) NOT NULL, altura VARCHAR(6), genero VARCHAR(1) NOT NULL, PRIMARY KEY(id))")) {
                stmt.execute();
            }
            
            try (PreparedStatement stmt2 = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS admins (nombre VARCHAR(50) NOT NULL, contrasena varchar(32) NOT NULL, correo VARCHAR(60) NOT NULL, permisos VARCHAR(25) NOT NULL, PRIMARY KEY(correo))")) {
                stmt2.execute();
            }
        }
        
        return conexion;
    }

    public Connection getConnection() throws SQLException {
        return conexion.isClosed() && conexion == null ? null : conexion;
    }

    public boolean checkConnection() throws SQLException {
        return !conexion.isClosed() && conexion != null;
    }
}
