package controladores;

public interface Controlador {
    
    boolean existsEntidad(Object entidad) throws Exception;
    void addEntidad(String[] propiedades) throws Exception;
    Object getEntidad(String propiedad) throws Exception;
    void deleteEntidad(String propiedad) throws Exception;
}
