package controladores;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    private Statement update(String ins) throws Exception {
        try (Statement stmt = this.getConn().createStatement()) {
            stmt.executeUpdate(ins);
            return stmt;
        }
    }

    public AdministradorControlador() throws Exception {
        lista_administradores = new ArrayList<>();

        try {
            try (PreparedStatement stmt = this.getConn().prepareStatement("SELECT * FROM admins")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Administrador admin = new Administrador();
                        admin.setNombre(rs.getString("nombre"));
                        admin.setContrasena(rs.getString("contrasena"));
                        admin.setCorreo(rs.getString("correo"));

                        if (rs.getString("permisos").contains(",")) {
                            String[] permisos = rs.getString("permisos").split("\\,");
                            admin.setPermisos(Arrays.asList(permisos));
                        } else {
                            if (rs.getString("permisos").equals("agregar") || rs.getString("permisos").equals("modificar") || rs.getString("permisos").equals("borrar")) {
                                admin.setPermisos(Arrays.asList(rs.getString("permisos")));
                            } else {
                                throw new NullPointerException();
                            }
                        }

                        if (!this.getLista_admins().contains(admin)) {
                            this.getLista_admins().add(admin);
                        }
                    }

                    this.getLista_admins().forEach(a -> {
                        System.out.println(a.toString());
                    });
                }
            }
        } catch (SQLException ex) {
            System.out.println("Un error ha ocurrido >> " + ex.getMessage());
        }
    }

    @Override
    public boolean existsEntidad(String propiedad) throws Exception {
        try {
            try (PreparedStatement stmt = this.getConn().prepareStatement("SELECT * FROM admin WHERE nombre=?")) {
                stmt.setString(1, propiedad);
                try (ResultSet rs = stmt.executeQuery()) {
                    return rs.next();
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

        if (propiedades[3].contains(",")) {
            String[] permisos = propiedades[3].split("\\,");
            admin.setPermisos(Arrays.asList(permisos));
        } else {
            if (propiedades[3].equals("agregar") || propiedades[3].equals("modificar") || propiedades[3].equals("borrar")) {
                admin.setPermisos(Arrays.asList(propiedades[3]));
            } else {
                throw new NullPointerException();
            }
        }

        try (PreparedStatement stmt = this.getConn().prepareStatement("SELECT * FROM admins WHERE nombre=?")) {
            stmt.setString(1, admin.getNombre());
            try (ResultSet rs = stmt.executeQuery()) {
                if (!(rs.next())) {
                    try (PreparedStatement stmt2 = this.getConn().prepareStatement("INSERT INTO admins(nombre, contrasena, correo, permisos) VALUES (?,?,?,?)")) {
                        stmt2.setString(1, admin.getNombre());
                        stmt2.setString(2, admin.getContrasena());
                        stmt2.setString(3, admin.getCorreo());
                        stmt2.setString(4, propiedades[3]);
                        stmt2.executeUpdate();
                    }
                }
            }
        }

        if (!(this.existsEntidad(admin.getCorreo()))) {
            this.getLista_admins().add(admin);
        }
    }

    @Override
    public Object getEntidad(String propiedad) throws Exception {
        try (PreparedStatement stmt = this.getConn().prepareStatement("SELECT * FROM admins WHERE nombre=?")) {
            stmt.setString(1, propiedad);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Administrador admin = new Administrador();
                    admin.setNombre(rs.getString("nombre"));
                    admin.setContrasena(rs.getString("contrasena"));
                    admin.setCorreo(rs.getString("correo"));

                    if (rs.getString("permisos").contains(",")) {
                        String[] permisos = rs.getString("permisos").split("\\,");
                        admin.setPermisos(Arrays.asList(permisos));
                    } else {
                        if (rs.getString("permisos").equals("agregar") || rs.getString("permisos").equals("modificar") || rs.getString("permisos").equals("borrar")) {
                            admin.setPermisos(Arrays.asList(rs.getString("permisos")));
                        } else {
                            throw new NullPointerException();
                        }
                    }

                    return admin;
                }
            }
        }
        return null;
    }

    @Override
    public void deleteEntidad(String propiedad) throws Exception {
        try (PreparedStatement stmt = this.getConn().prepareStatement("DELETE FROM admins WHERE nombre=?")) {
            stmt.setString(1, propiedad);
            stmt.executeUpdate();
        }

        Administrador admin = (Administrador) this.getEntidad(propiedad);
        if (this.existsEntidad(propiedad)) {
            this.getLista_admins().remove(admin);
        }
    }
}
