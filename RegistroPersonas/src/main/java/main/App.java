package main;

import conector.MySQLConector;
import controladores.Controlador;
import controladores.PersonaControlador;
import ventanas.VentanaBorrar;
import ventanas.VentanaPrincipal;
import ventanas.VentanaRegistro;

public class App {
    
    private static MySQLConector mySQL;
    
    public static MySQLConector getMySQL() throws Exception {
        if (mySQL != null) {
            return mySQL;
        } else {
            return null;
        }
    }
    
    private static VentanaRegistro ventanaRegistro;
    
    public static VentanaRegistro getVentanaRegistro() {
        return ventanaRegistro;
    }
    
    private static VentanaPrincipal ventanaPrincipal;
    
    public static VentanaPrincipal getVentanaPrincipal() {
        if (ventanaPrincipal != null) {
            return ventanaPrincipal;
        } else {
            return null;
        }
    }
    
    private static VentanaBorrar ventanaBorrar;
    
    public static VentanaBorrar getVentanaBorrar() {
        if (ventanaBorrar != null) {
            return ventanaBorrar;
        } else {
            return null;
        }
    }
    
    private static Controlador personaControlador;
    
    public static Controlador getPersonaControlador() {
        if (personaControlador != null) {
            return personaControlador;
        } else {
            return null;
        }
    }
    
    public static void main(String[] args) {
        try {
            mySQL = new MySQLConector("paulo", "D]j!XzMvI*ostEd4", "localhost", 3306, "regpersonas");
            getMySQL().conectar();
            
            personaControlador = new PersonaControlador();
            
            ventanaPrincipal = new VentanaPrincipal();
            getVentanaPrincipal().setVisible(true);
            
            ventanaRegistro = new VentanaRegistro();
            ventanaBorrar = new VentanaBorrar();
        } catch (Exception ex) {
            System.out.println("Un error ha ocurrido >> " + ex.getMessage());
        }
    }
}
