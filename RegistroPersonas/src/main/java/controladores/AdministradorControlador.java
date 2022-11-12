package controladores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.App;
import objetos.Administrador;

/*
* Clase hecha para evitar tantas consultas
* A la base de datos MySQL
 */
public class AdministradorControlador implements Controlador {

    private List<Administrador> lista_administradores;

    public List<Administrador> getLista_admins() {
        return lista_administradores;
    }

    private Connection conn;

    private Connection getConn() throws Exception {
        return App.getMySQL() == null ? null : App.getMySQL().getConnection();
    }

    private Statement execute(String ins) throws Exception {
        try (Statement stmt = this.getConn().createStatement()) {
            stmt.execute(ins);
            return stmt;
        }
    }

    public AdministradorControlador() throws Exception {
        lista_administradores = new ArrayList<>();

        try {
            try (Statement stmt = this.getConn().createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM admins")) {
                    while (rs.next()) {
                        Administrador admin = new Administrador();
                        admin.setNombre(rs.getString("nombre"));
                        admin.setContrasena(rs.getString("contrasena"));
                        admin.setCorreo(rs.getString("correo"));

                        if (!this.getLista_admins().contains(admin)) {
                            this.getLista_admins().add(admin);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Un error ha ocurrido >> " + ex.getMessage());
        }
    }

    @Override
    public boolean existsEntidad(String propiedad) throws Exception {
        try {
            try (Statement stmt = this.getConn().createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM admin WHERE correo = '" + propiedad + "'")) {
                    if (rs.next()) {
                        Administrador admin = new Administrador();
                        admin.setNombre(rs.getString("nombre"));
                        admin.setCorreo(rs.getString("correo"));
                        admin.setContrasena(rs.getString("contrasena"));
                        return this.getLista_admins().contains(admin);
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Un error ha ocurrido >> " + ex.getClass().getName() + " ERROR: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public void addEntidad(String[] propiedades) throws Exception {

    }

    @Override
    public Object getEntidad(String propiedad) throws Exception {
        return null;
    }

    @Override
    public void deleteEntidad(String propiedad) throws Exception {

    }

    public boolean comprobar_contrasena(String correo, String contrasena) throws Exception {
        try {
            try (Statement stmt = this.getConn().createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM admin WHERE correo = '" + correo + "' AND contrasena = SHA1('" + contrasena + "')")) {
                    return rs.next();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Un error ha ocurrido >> " + ex.getClass().getName() + " ERROR: " + ex.getMessage());
        }
        return false;
    }
}
