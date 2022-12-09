package main;

import frames.Main_Frame;
import frames.MySQLConfig_Frame;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import json.JSON_Configuration;
import manager.AdministratorManager;
import org.json.simple.parser.ParseException;
import utils.HikariConnection;

public class Main {

    private static HikariConnection hikari;

    private static JSON_Configuration json;

    private static AdministratorManager adminManager;

    private static Main_Frame mainFrame;

    public static void main(String[] args) {
        json = new JSON_Configuration();
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

                if (getMySQLConnection() != null) {
                    try (PreparedStatement stmt = getMySQLConnection().prepareStatement("CREATE TABLE IF NOT EXISTS administrators (name VARCHAR(32) NOT NULL, mail VARCHAR(60) NOT NULL, password VARCHAR(16) NOT NULL, address TEXT, perms VARCHAR(18) NOT NULL, PRIMARY KEY(mail))")) {
                        stmt.execute();
                    }

                    adminManager = new AdministratorManager();
                    if (getAdministratorManager().getAdministrator_list().isEmpty()) {
                        File session_directory = new File("C:\\Users\\" + System.getProperty("user.name") + "\\regpersonas_files\\sessions\\");
                        if (session_directory.exists() && session_directory.isDirectory()) {
                            File[] files = session_directory.listFiles();
                            if (files != null) {
                                for (File file : files) {
                                    file.delete();
                                }
                                Files.deleteIfExists(Path.of(session_directory.getAbsolutePath()));
                            } else {
                                Files.deleteIfExists(Path.of(session_directory.getAbsolutePath()));
                            }
                            System.out.println("Borrando carpeta de sesiones debido a que la lista de administradores esta vacia.");
                        }
                    }

                    mainFrame = new Main_Frame();
                    mainFrame.setVisible(true);

                } else {
                    MySQLConfig_Frame mysqlconfig = new MySQLConfig_Frame();
                    mysqlconfig.setVisible(true);
                }
            } else {
                MySQLConfig_Frame mysqlconfig = new MySQLConfig_Frame();
                mysqlconfig.setVisible(true);
            }
        } catch (IOException | ParseException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getMySQLConnection() throws SQLException {
        return hikari != null && !hikari.getConnection().isClosed() && hikari.getConnection() != null ? hikari.getConnection() : null;
    }

    public static JSON_Configuration getJSON_Configuration() {
        return json;
    }

    public static AdministratorManager getAdministratorManager() {
        return adminManager;
    }

    public static Main_Frame getMain_Frame() {
        return mainFrame;
    }

    public static String formatDate(LocalDateTime date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return format.format(date);
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
