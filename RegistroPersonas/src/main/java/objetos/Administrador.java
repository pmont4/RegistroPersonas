package objetos;

import java.util.List;

public class Administrador {
    
    private String nombre;
    private String contrasena;
    private String correo;
    
    private List<String> permisos;

    public Administrador(String nombre, String contrasena, String correo, List<String> permisos) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.correo = correo;
        this.permisos = permisos;
    }

    public Administrador() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<String> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<String> permisos) {
        this.permisos = permisos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Administrador{nombre=").append(nombre);
        sb.append(", contrasena=").append(contrasena);
        sb.append(", correo=").append(correo);
        sb.append(", permisos=").append(permisos);
        sb.append('}');
        return sb.toString();
    }
}
