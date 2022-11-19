package main;

import conector.MySQLConector;
import controladores.AdministradorControlador;
import controladores.Controlador;
import controladores.PersonaControlador;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import objetos.Administrador;
import ventanas.VentanaBorrar;
import ventanas.VentanaDatosMySQL;
import ventanas.VentanaLogIn;
import ventanas.VentanaPrincipal;
import ventanas.VentanaRegistrarAdmin;
import ventanas.VentanaRegistro;

public class App {

    private static MySQLConector mySQL;

    public static MySQLConector getMySQL() throws Exception {
        return mySQL == null ? null : mySQL;
    }

    private static VentanaLogIn ventanaLogIn;

    public static VentanaLogIn getVentanaLogIn() {
        return ventanaLogIn == null ? null : ventanaLogIn;
    }

    private static VentanaRegistro ventanaRegistro;

    public static VentanaRegistro getVentanaRegistro() {
        return ventanaRegistro == null ? null : ventanaRegistro;
    }

    private static VentanaPrincipal ventanaPrincipal;

    public static VentanaPrincipal getVentanaPrincipal() {
        return ventanaPrincipal == null ? null : ventanaPrincipal;
    }

    private static VentanaBorrar ventanaBorrar;

    public static VentanaBorrar getVentanaBorrar() {
        return ventanaBorrar == null ? null : ventanaBorrar;
    }

    private static Controlador personaControlador;

    public static Controlador getPersonaControlador() {
        return personaControlador == null ? null : personaControlador;
    }

    private static Controlador adminControlador;

    public static Controlador getAdminControlador() {
        return adminControlador == null ? null : adminControlador;
    }

    private static void initVentanas() throws Exception {
        ventanaLogIn = new VentanaLogIn();
        ventanaPrincipal = new VentanaPrincipal();
        ventanaRegistro = new VentanaRegistro();
        ventanaBorrar = new VentanaBorrar();

        try (PreparedStatement stmt = getMySQL().getConnection().prepareStatement("SELECT * FROM admins")) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    getVentanaLogIn().setVisible(true);
                } else {
                    VentanaRegistrarAdmin admin = new VentanaRegistrarAdmin();
                    admin.setVisible(true);
                }
            }
        }
    }

    private static void initControladores() throws Exception {
        personaControlador = new PersonaControlador();
        adminControlador = new AdministradorControlador();
    }

    public static File directorio_config;

    public static VentanaDatosMySQL config;

    public static void main(String[] args) throws IOException {

        System.out.println(System.getProperty("user.name"));

        try {
            boolean directorio_disponible = false;

            directorio_config = new File("C:\\Users\\" + System.getProperty("user.name") + "\\regpersonas");
            if (!(directorio_config.exists())) {
                if (directorio_config.mkdirs()) {
                    System.out.println("Directorio correctamente hecho");
                    directorio_disponible = true;
                }
            } else {
                directorio_disponible = true;
            }

            if (directorio_disponible) {
                File archivo = new File(directorio_config.getAbsolutePath() + "\\config.txt");
                if (archivo.exists()) {
                    String linea = new String(Files.readAllBytes(Paths.get(archivo.getAbsolutePath())));
                    System.out.println(linea);

                    String[] split = linea.split("\\,");
                    String usuario = "";
                    String contrasena = "";
                    String database = "";
                    String host = "";
                    Integer port = 0;
                    String ssl = "";

                    if (split[0].contains("usuario")) {
                        String[] usuario_split = split[0].split("\\:");
                        usuario = usuario_split[1];
                    }
                    if (split[1].contains("contraseña")) {
                        String[] contra_split = split[1].split("\\:");
                        contrasena = contra_split[1];
                    }
                    if (split[2].contains("database")) {
                        String[] database_split = split[2].split("\\:");
                        database = database_split[1];
                    }
                    if (split[3].contains("host")) {
                        String[] host_split = split[3].split("\\:");
                        host = host_split[1];
                    }
                    if (split[4].contains("port")) {
                        String[] port_split = split[4].split("\\:");
                        port = Integer.parseInt(port_split[1]);
                    }
                    if (split[5].contains("ssl")) {
                        String[] ssl_split = split[5].split("\\:");
                        ssl = ssl_split[1];
                    }

                    mySQL = new MySQLConector();
                    mySQL.setDatabase(database);
                    mySQL.setUser(usuario);
                    mySQL.setHost(host);
                    mySQL.setPassword(contrasena);
                    mySQL.setPort(port);
                    mySQL.setSSL(ssl);
                    mySQL.configure();
                    mySQL.connect();

                    if (mySQL.checkConnection()) {
                        initControladores();
                        initVentanas();
                    } else {
                        config = new VentanaDatosMySQL();
                        config.setVisible(true);
                    }
                } else {
                    config = new VentanaDatosMySQL();
                    config.setVisible(true);
                }
            }
        } catch (Exception ex) {
            System.out.println("Un error ha ocurrido: " + ex.getMessage());
        }
    }

    private static Administrador adminOnline;

    public static void setAdminOnline(Administrador admin) {
        adminOnline = admin;
    }

    public static Administrador getAdminOnline() {
        return adminOnline == null ? null : adminOnline;
    }

}
