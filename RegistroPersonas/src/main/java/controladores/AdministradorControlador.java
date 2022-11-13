package controladores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
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
                        
                        String[] permisos = rs.getString("permisos").split("\\,");
                        admin.setPermisos(Arrays.asList(permisos));
                        System.out.println(admin.getPermisos() + " " + admin.getNombre());

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
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM admin WHERE correo = " + propiedad)) {
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
        Administrador admin = new Administrador();
        admin.setNombre(propiedades[0]);
        admin.setContrasena(propiedades[1]);
        admin.setCorreo(propiedades[2]);
        
        try (Statement stmt = this.getConn().createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM admins WHERE correo = " + admin.getCorreo())) {
                if (!(rs.next())) {
                    this.execute("INSERT INTO admins (nombre, contrasena, correo) VALUES ('"
                                    + admin.getNombre() + "', "
                                    + "SHA1('" + admin.getContrasena() + "'), "
                                    + "'" + admin.getCorreo() + "')");
                }
            }
        }
        
        if (!(this.existsEntidad(admin.getCorreo()))) {
            this.getLista_admins().add(admin);
        }
    }

    @Override
    public Object getEntidad(String propiedad) throws Exception {
        try (Statement stmt = this.getConn().createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM admins WHERE correo = " + propiedad)) {
                if (rs.next()) {
                    Administrador admin = new Administrador();
                    admin.setNombre(rs.getString("nombre"));
                    admin.setContrasena(rs.getString("contrasena"));
                    admin.setCorreo(rs.getString("correo"));
                    return admin;
                }
            }
        }
        return null;
    }

    @Override
    public void deleteEntidad(String propiedad) throws Exception {
        this.execute("DELETE FROM admins WHERE correo = " + propiedad);
        
        Administrador admin = (Administrador) this.getEntidad(propiedad);
        if (this.existsEntidad(propiedad)) {
            this.getLista_admins().remove(admin);
        }
    }

    public boolean comprobar_contrasena(String correo, String contrasena) throws Exception {
        try {
            try (Statement stmt = this.getConn().createStatement()) {
                try (ResultSet rs = stmt.executeQuery("SELECT * FROM admin WHERE correo = " + correo + " AND contrasena = SHA1(" + contrasena + ")")) {
                    return rs.next();
                }
            }
        } catch (SQLException ex) {
            System.out.println("Un error ha ocurrido >> " + ex.getClass().getName() + " ERROR: " + ex.getMessage());
        }
        return false;
    }
}
