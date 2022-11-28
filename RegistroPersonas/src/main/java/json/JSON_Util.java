package json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import main.App;
import objetos.Administrador;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSON_Util {

    private File archivo_json;

    public JSON_Util() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = format.format(now);

        this.archivo_json = new File(App.directorio_config.getAbsolutePath() + "\\session_admin_" + date + ".json");
        if (!(this.archivo_json.exists())) {
            try {
                if (this.archivo_json.createNewFile());
            } catch (IOException ex) {
                System.out.println("Un error ha ocurrido: CLASE>" + this.getClass().getName() + " METODO: CONTRUCT_JSON" + " ERROR: " + ex.getMessage());
            }
        }
    }

    private File getArchivo_json() {
        return this.archivo_json.exists() ? this.archivo_json : null;
    }

    public void escribir_archivo(Administrador admin) throws IOException {
        JSONObject objeto = new JSONObject();
        objeto.put("nombre", admin.getNombre());
        objeto.put("contrasena", admin.getContrasena());
        objeto.put("correo", admin.getCorreo());

        JSONArray permisos = new JSONArray();
        for (int i = 0; i < admin.getPermisos().size(); i++) {
            JSONObject obj = new JSONObject();
            obj.put("permiso_" + i, admin.getPermisos().get(i));
            permisos.add(obj);
        }
        objeto.put("permisos", permisos);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.getArchivo_json()))) {
            writer.write(objeto.toJSONString());
            writer.flush();
        }
    }

    public Administrador getJSON_Admin() throws IOException, ParseException {
        JSONParser parse = new JSONParser();
        Administrador admin = new Administrador();

        try (FileReader reader = new FileReader(this.getArchivo_json())) {
            Object obj = parse.parse(reader);
            JSONObject admin_obj = (JSONObject) obj;

            String nombre = String.valueOf(admin_obj.get("nombre"));
            String contrasena = String.valueOf(admin_obj.get("contrasena"));
            String correo = String.valueOf(admin_obj.get("correo"));

            List<String> permisos = new ArrayList<>();
            JSONArray permisos_obj = (JSONArray) admin_obj.get("permisos");

            permisos_obj.forEach(o -> {
                JSONObject p = (JSONObject) o;
                permisos.add((String) p.toString());
            });

            admin.setNombre(nombre);
            admin.setContrasena(contrasena);
            admin.setCorreo(correo);

            List<String> nueva_lista = new ArrayList<>();
            if (!permisos.isEmpty()) {
                permisos.forEach(p -> {
                    String[] split = p.split(":");
                    String cadena = split[1].replace("\"", "").replace("}", "");
                    nueva_lista.add(cadena);
                });
            }
            admin.setPermisos(nueva_lista);
        }

        return admin;
    }

}
