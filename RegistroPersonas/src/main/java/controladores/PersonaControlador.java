package controladores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.SourceVersion;
import main.App;
import objetos.Persona;

/*
* Controlador del objecto persona
* Control de instrucciones MySQL y demas
 */

public class PersonaControlador implements Controlador {

    private List<Persona> lista_personas;

    public List<Persona> getLista_personas() {
        return lista_personas;
    }
    
    private Connection getConn() throws Exception {
        return App.getMySQL() == null ? null : App.getMySQL().getConnection();
    }
    
    private Statement execute(String ins) throws Exception {
        try (Statement stmt = this.getConn().createStatement()) {
            stmt.execute(ins);
            return stmt;
        }
    }

    public PersonaControlador() throws Exception {
        lista_personas = new ArrayList<>();

        try {
            try (Statement stmt = this.getConn().createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM personas")) {
                    while (rs.next()) {
                        Persona persona = new Persona();
                        persona.setId(rs.getInt("id"));
                        persona.setNombre(rs.getString("nombre"));
                        persona.setEdad(rs.getInt("edad"));
                        persona.setAltura(rs.getString("altura"));
                        persona.setGenero(rs.getString("altura").charAt(0));

                        if (!getLista_personas().contains(persona)) {
                            getLista_personas().add(persona);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Un error ha ocurrido >> " + ex.getMessage());
        }
    }

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public void addEntidad(String[] propiedades) throws Exception {
        Persona persona = new Persona();
        persona.setId(Integer.parseInt(propiedades[0]));
        persona.setNombre(propiedades[1]);
        persona.setEdad(Integer.parseInt(propiedades[2]));
        persona.setAltura(propiedades[3]);
        persona.setGenero(propiedades[4].charAt(0));
        
        try (Statement stmt = this.getConn().createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM personas WHERE id = " + persona.getId())) {
                if (!(rs.next())) {
                    this.execute("INSERT INTO personas (id, nombre, edad, altura, genero) VALUES (0, "
                                + "'" + persona.getNombre() + "', "
                                + persona.getEdad() + ", "
                                + "'" + persona.getAltura() + "', "
                                + "'" + persona.getGenero() + "')");
                }
            }
        }
        
        if (!(this.existsEntidad(persona.getId().toString()))) {
            this.getLista_personas().add(persona);
        }
    }

    @Override
    public Object getEntidad(String propiedad) throws Exception {
        try (Statement stmt = this.getConn().createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM personas WHERE id = " + Integer.parseInt(propiedad))) {
                if (rs.next()){
                    Persona persona = new Persona();
                    persona.setId(rs.getInt("id"));
                    persona.setNombre(rs.getString("nombre"));
                    persona.setEdad(rs.getInt("edad"));
                    persona.setAltura(rs.getString("altura"));
                    persona.setGenero(rs.getString("genero").charAt(0));
                    return persona;
                }
            }
        }
        return null;
    }

    @Override
    public void deleteEntidad(String propiedad) throws Exception {
        this.execute("DELETE FROM personas WHERE id = " + Integer.parseInt(propiedad));
        
        Persona persona = (Persona) this.getEntidad(propiedad);
        if (this.existsEntidad(propiedad)) {
            this.getLista_personas().remove(persona);
        }
    }

    @Override
    public boolean existsEntidad(String propiedad) throws Exception {
        try (Statement stmt = this.getConn().createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM personas WHERE id = " + Integer.parseInt(propiedad))) {
                if (rs.next()) {
                    Persona persona = new Persona();
                    persona.setId(rs.getInt("id"));
                    persona.setNombre(rs.getString("nombre"));
                    persona.setEdad(rs.getInt("edad"));
                    persona.setAltura(rs.getString("altura"));
                    persona.setGenero(rs.getString("genero").charAt(0));
                    return this.getLista_personas().contains(persona);
                }
            }
        }
        return false;
    }
}
