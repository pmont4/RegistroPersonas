package controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public PersonaControlador() throws Exception {
        lista_personas = new ArrayList<>();

        try {
            try (PreparedStatement stmt = this.getConn().prepareStatement("SELECT * FROM personas")) {
                try (ResultSet rs = stmt.executeQuery()) {
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
        
        try (PreparedStatement stmt = this.getConn().prepareStatement("SELECT * FROM personas WHERE nombre=?")) {
            stmt.setString(1, persona.getNombre());
            try (ResultSet rs = stmt.executeQuery()) {
                if (!(rs.next())) {
                    try (PreparedStatement stmt2 = this.getConn().prepareStatement("INSERT INTO personas(id, nombre, edad, altura, genero) VALUES (?,?,?,?,?)")) {
                        stmt2.setInt(1, 0);
                        stmt2.setString(2, persona.getNombre());
                        stmt2.setInt(3, persona.getEdad());
                        stmt2.setString(4, persona.getAltura());
                        stmt2.setString(5, persona.getGenero().toString());
                        stmt2.executeUpdate();
                    }
                }
            }
        }
        
        if (!(this.existsEntidad(persona.getId().toString()))) {
            this.getLista_personas().add(persona);
        }
    }

    @Override
    public Object getEntidad(String propiedad) throws Exception {
        try (PreparedStatement stmt = this.getConn().prepareStatement("SELECT * FROM personas WHERE id=?")) {
            stmt.setInt(1, Integer.parseInt(propiedad));
            try (ResultSet rs = stmt.executeQuery()) {
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
        try (PreparedStatement stmt = this.getConn().prepareStatement("DELETE FROM personas WHERE id=?")) {
            stmt.setInt(1, Integer.parseInt(propiedad));
            stmt.executeUpdate();
        }
        
        Persona persona = (Persona) this.getEntidad(propiedad);
        if (this.existsEntidad(propiedad)) {
            this.getLista_personas().remove(persona);
        }
    }

    @Override
    public boolean existsEntidad(String propiedad) throws Exception {
        try (PreparedStatement stmt = this.getConn().prepareStatement("SELECT * FROM personas WHERE id=?")) {
            stmt.setInt(1, Integer.parseInt(propiedad));
            try (ResultSet rs = stmt.executeQuery()) {
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
