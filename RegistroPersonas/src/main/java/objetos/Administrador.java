package objetos;

import java.time.LocalDateTime;
import java.util.List;

public class Administrador {
    
    private String nombre;
    private String contrasena;
    private String correo;
    
    private List<String> permisos;
    
    private List<String> lista_registro;
    private LocalDateTime ultima_sesion;
    private Integer numero_registros;

    public Administrador(String nombre, String contrasena, String correo, List<String> permisos, List<String> lista_registro, LocalDateTime ultima_sesion, Integer numero_registros) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.correo = correo;
        this.permisos = permisos;
        this.lista_registro = lista_registro;
        this.ultima_sesion = ultima_sesion;
        this.numero_registros = numero_registros;
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

    public List<String> getLista_registro() {
        return lista_registro;
    }

    public void setLista_registro(List<String> lista_registro) {
        this.lista_registro = lista_registro;
    }

    public LocalDateTime getUltima_sesion() {
        return ultima_sesion;
    }

    public void setUltima_sesion(LocalDateTime ultima_sesion) {
        this.ultima_sesion = ultima_sesion;
    }

    public Integer getNumero_registros() {
        return numero_registros;
    }

    public void setNumero_registros(Integer numero_registros) {
        this.numero_registros = numero_registros;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Administrador {nombre=").append(nombre);
        sb.append(", contrasena=").append(contrasena);
        sb.append(", correo=").append(correo);
        sb.append(", permisos=").append(permisos);
        sb.append('}');
        return sb.toString();
    }
}
