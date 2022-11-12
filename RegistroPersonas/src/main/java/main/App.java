package main;

import conector.MySQLConector;
import controladores.AdministradorControlador;
import controladores.Controlador;
import controladores.PersonaControlador;
import ventanas.VentanaBorrar;
import ventanas.VentanaPrincipal;
import ventanas.VentanaRegistro;

public class App {
    
    private static MySQLConector mySQL;
    public static MySQLConector getMySQL() throws Exception {
        return mySQL == null ? null : mySQL;
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
    
    public static void main(String[] args) {
        try {
            mySQL = new MySQLConector("paulo", "D]j!XzMvI*ostEd4", "localhost", 3306, "regpersonas");
            getMySQL().conectar();
            
            personaControlador = new PersonaControlador();
            adminControlador = new AdministradorControlador();
            
            ventanaPrincipal = new VentanaPrincipal();
            getVentanaPrincipal().setVisible(true);
            
            ventanaRegistro = new VentanaRegistro();
            ventanaBorrar = new VentanaBorrar();
        } catch (Exception ex) {
            System.out.println("Un error ha ocurrido >> " + ex.getMessage());
        }
    }
}
