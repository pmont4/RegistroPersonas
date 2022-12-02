package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import manager.AdministratorManager;
import manager.PersonManager;
import utils.HikariConnection;

/*
 Recode total del proyecto
*/

public class Main {
    
    private static HikariConnection hikari;
    
    private static AdministratorManager administratorManager;
    private static PersonManager personManager;
    
    public static void main(String[] args) {
        try {
            hikari = new HikariConnection();
            hikari.setUser("paulo");
            hikari.setPassword("D]j!XzMvI*ostEd4");
            hikari.setHost("localhost");
            hikari.setDatabase("regpersonas");
            hikari.setPort(3306);
            hikari.configureProperties();
            hikari.connect();
            
            if (getMySQLConnection() != null) {
                PreparedStatement stmt = getMySQLConnection().prepareStatement("CREATE TABLE IF NOT EXISTS administrators (name VARCHAR(32) NOT NULL, mail VARCHAR(60) NOT NULL, password VARCHAR(16) NOT NULL, address TEXT, perms VARCHAR(10) NOT NULL)");
                stmt.execute();
                stmt = getMySQLConnection().prepareStatement("CREATE TABLE IF NOT EXISTS persons (id INT NOT NULL AUTO_INCREMENT, name varchar(45) NOT NULL, age INT NOT NULL, height VARCHAR(20), gender VARCHAR(1), PRIMARY KEY(id))");
                stmt.execute();
                stmt.close();
                
                administratorManager = new AdministratorManager();
                personManager = new PersonManager();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static String parseDate(LocalDateTime date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return format.format(date);
    }
    
    public static Connection getMySQLConnection() throws SQLException {
        return hikari != null && !hikari.getConnection().isClosed() && hikari.getConnection() != null ? hikari.getConnection() : null;
    }
    
    public static AdministratorManager getAdministratorManager() {
        return administratorManager;
    }
    
    public static PersonManager getPersonManager() {
        return personManager;
    }

}
