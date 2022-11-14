package main;

import conector.MySQLConector;
import controladores.AdministradorControlador;
import controladores.Controlador;
import controladores.PersonaControlador;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import objetos.Administrador;
import ventanas.VentanaBorrar;
import ventanas.VentanaLogIn;
import ventanas.VentanaPrincipal;
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
        
        getVentanaLogIn().setVisible(true);
    }
    
    private static void initControladores() throws Exception {
        personaControlador = new PersonaControlador();
        adminControlador = new AdministradorControlador();
    }
    
    public static void main(String[] args) {
        try {
            mySQL = new MySQLConector();
            mySQL.setDatabase("regpersonas");
            mySQL.setUser("paulo");
            mySQL.setHost("localhost");
            mySQL.setPassword("D]j!XzMvI*ostEd4");
            mySQL.setPort(3306);
            mySQL.setSSL("false");
            mySQL.configure();
            mySQL.connect();
            
            initControladores();
            initVentanas();
        } catch (Exception ex) {
            System.out.println("Un error ha ocurrido >> " + ex.getMessage());
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
