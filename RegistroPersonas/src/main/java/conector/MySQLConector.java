package conector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLConector {
    
    private String user;
    private String password;
    private String host;
    private Integer port;
    private String database;
    
    private Connection connection;

    public MySQLConector(String user, String password, String host, Integer port, String database) {
        this.user = user;
        this.password = password;
        this.host = host;
        this.port = port;
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Connection getConnection() {
        return connection;
    }
    
    /*
    * Conectar a la base de datos con los parametros ingresados en los set's
    */
    
    public void conectar() {
        if (this.getConnection() == null) {
            String url = "jdbc:mysql://" + this.getHost() + ":" + this.getPort() + "/" + this.getDatabase();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                this.connection = DriverManager.getConnection(url, this.getUser(), this.getPassword());
                
                if (this.getConnection() != null) {
                    this.execute("CREATE TABLE IF NOT EXISTS personas ('id' INT NOT NULL AUTO_INCREMENT, 'nombre' VARCHAR(50) NOT NULL, 'altura' VARCHAR(6), 'genero' VARCHAR(1) NOT NULL, PRIMARY KEY('id'))");
                    this.execute("CREATE TABLE IF NOT EXISTS admins ('nombre' VARCHAR(50) NOT NULL, 'contrasena' varchar(32) NOT NULL, 'correo' VARCHAR(60) NOT NULL, PRIMARY KEY('correo'))");
                }
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.println("Un error ha ocurrido >> " + ex.getLocalizedMessage());
            }
        }
    }
    
    /*
    * Desconectar de la base de datos
    */
    
    public void desconectar() {
        try {
            if (!(this.getConnection().isClosed() && this.getConnection() == null)) {
                this.getConnection().close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQLConector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void execute(String ins) throws SQLException {
        try (Statement stmt = this.getConnection().createStatement()) {
            stmt.execute(ins);
        }
    }
}
