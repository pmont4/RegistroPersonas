package main;

import entities.Administrator;
import frames.AdministratorRegistration_Frame;
import frames.LogIn_Frame;
import frames.Main_Frame;
import frames.MySQLConfig_Frame;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.swing.JOptionPane;
import json.JSON_Configuration;
import manager.AdministratorManager;
import manager.PersonManager;
import org.json.simple.parser.ParseException;
import utils.HikariConnection;

/*
 Recode total del proyecto
 */
public class Main {

    private static HikariConnection hikari;

    private static AdministratorManager administratorManager;
    private static PersonManager personManager;

    private static JSON_Configuration json_configuration;
    
    private static AdministratorRegistration_Frame adminRegis;
    
    private static Administrator admin_online;
    
    /*
    * ventanas accesibles
    */
    
    private static Main_Frame main_frame;

    public static void main(String[] args) {
        json_configuration = new JSON_Configuration();
        try {
            String config_string = getJSON_Configuration().getMySQLConfiguration();
            if (!config_string.equals("")) {

                String[] split = config_string.split(":");
                String user = split[0];
                String password = split[1];
                String host = split[2];
                String database = split[3];
                int port = Integer.parseInt(split[4]);
                String ssl = split[5];

                hikari = new HikariConnection();
                hikari.setUser(user);
                hikari.setPassword(password);
                hikari.setHost(host);
                hikari.setDatabase(database);
                hikari.setPort(port);
                hikari.setSsl(ssl);
                hikari.configureProperties();
                hikari.connect();

                if (getMySQLConnection() != null && !getMySQLConnection().isClosed()) {
                    PreparedStatement stmt = getMySQLConnection().prepareStatement("CREATE TABLE IF NOT EXISTS people (id INT NOT NULL AUTO_INCREMENT, name varchar(45) NOT NULL, birth_date VARCHAR(45) NOT NULL, height VARCHAR(22), gender VARCHAR(1), PRIMARY KEY(id))");
                    stmt.execute();
                    
                    administratorManager = new AdministratorManager();
                    personManager = new PersonManager();
          
                    main_frame = new Main_Frame();

                    stmt = getMySQLConnection().prepareStatement("SELECT * FROM administrators");
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            LogIn_Frame login = new LogIn_Frame();
                            if (!getJSON_Configuration().existsSessionFile()) {
                                login.setVisible(true);
                            } else {
                                Optional<Administrator> opt = getJSON_Configuration().getCurrentAdminInJSONFile();
                                if (opt.isPresent()) {
                                    Administrator admin = opt.get();
                                    setAdministratorOnline(admin);
                                    
                                    main_frame.updateTitle("Registro personas (Administrador conectado: " + admin.getName() + ")");
                                    main_frame.setVisible(true);
                                } else {
                                    login.setVisible(true);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontraron administradores en la base de datos, por favor ejecutar la aplicacion de registro de administradores y registrar al menos 1.", "Administradores", JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }
                    }
                    stmt.close();
                } else {
                    MySQLConfig_Frame mysqlconfig = new MySQLConfig_Frame();
                    mysqlconfig.setVisible(true);
                }
            } else {
                MySQLConfig_Frame mysqlconfig = new MySQLConfig_Frame();
                mysqlconfig.setVisible(true);
            }
        } catch (SQLException | IOException | ParseException ex) {
            ex.printStackTrace();
        }
    }

    public static String formatDate(LocalDateTime date) {
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

    public static JSON_Configuration getJSON_Configuration() {
        return json_configuration;
    }
    
    public static void setAdministratorOnline(Administrator admin) {
        admin_online = admin;
    }
    
    public static Administrator getAdministratorOnline() {
        return admin_online;
    }
    
    public static Main_Frame getMain_frame() {
        return main_frame;
    }
    
    public static boolean tableExists(String table) throws SQLException {
        boolean exists = false;
        try (ResultSet rs = Main.getMySQLConnection().getMetaData().getTables(null, null, table, null)) {
            while (rs.next()) {
                String name = rs.getString("TABLE_NAME");
                if (name != null && name.equals(table.toLowerCase())) {
                    exists = true;
                    break;
                }
            }
        }
        return exists;
    }
    
}
