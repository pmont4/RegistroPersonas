package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Administrator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Getter;
import main.Main;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSON_Configuration {

    @Getter
    private File main_directory;

    @Getter
    private File json_file_mysql;

    @Getter
    private File session_directory;

    public JSON_Configuration() {
        this.main_directory = new File("C:\\Users\\" + System.getProperty("user.name") + "\\regpersonas_files\\");
        if (!this.getMain_directory().exists()) {
            if (this.getMain_directory().mkdir()) {
                this.json_file_mysql = new File(this.getMain_directory().getAbsolutePath() + "\\mysql_config.json");
                if (!this.getJson_file_mysql().exists()) {
                    try {
                        if (this.getJson_file_mysql().createNewFile());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                this.session_directory = new File(this.getMain_directory().getAbsolutePath() + "\\sessions\\");
                if (!this.session_directory.exists()) {
                    if (this.session_directory.mkdir());
                }
            }
        } else {
            this.json_file_mysql = new File(this.getMain_directory().getAbsolutePath() + "\\mysql_config.json");
            if (!this.getJson_file_mysql().exists()) {
                try {
                    if (this.getJson_file_mysql().createNewFile());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            this.session_directory = new File(this.getMain_directory().getAbsolutePath() + "\\sessions\\");
            if (!this.session_directory.exists()) {
                if (this.session_directory.mkdir());
            }
        }
    }

    public void writeJsonConfigMySQL(@NotNull String user, @NotNull String password, @Nullable String host, @NotNull String database, @Nullable int port, @Nullable String ssl) throws IOException {
        JSONObject config_mysql = new JSONObject();
        config_mysql.put("user", user);
        config_mysql.put("password", password);
        if (host == null) {
            config_mysql.put("host", "localhost");
        } else {
            config_mysql.put("host", host);
        }
        config_mysql.put("database", database);
        if (port == 0) {
            config_mysql.put("port", 3306);
        } else {
            config_mysql.put("port", port);
        }
        if (ssl == null) {
            config_mysql.put("ssl", "false");
        } else {
            config_mysql.put("ssl", ssl);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.getJson_file_mysql()))) {
            writer.write(this.getEnhancedJSONString(config_mysql));
            writer.flush();
        }
    }

    public String getMySQLConfiguration() throws IOException, ParseException {
        StringBuilder sb = new StringBuilder();
        JSONParser parser = new JSONParser();

        if (this.getJson_file_mysql().length() > 0) {
            try (BufferedReader read = new BufferedReader(new FileReader(this.getJson_file_mysql()))) {
                Object object = parser.parse(read);
                if (object != null) {
                    JSONObject json_object = (JSONObject) object;

                    String user = (String) json_object.get("user");
                    String password = (String) json_object.get("password");
                    String host = (String) json_object.get("host");
                    String database = (String) json_object.get("database");
                    Long port = (Long) json_object.get("port");
                    String ssl = (String) json_object.get("ssl");

                    sb.append(user).append(":").append(password).append(":").append(host).append(":").append(database).append(":").append(port).append(":").append(ssl);
                    return sb.toString();
                } else {
                    return "";
                }
            }
        } else {
            return "";
        }
    }

    public void saveAdminSession(Administrator admin) throws IOException {
        File file = new File(this.getSession_directory().getAbsolutePath() + "\\session_" + Main.formatDate(LocalDateTime.now()) + ".json");
        if (!file.exists()) {
            if (file.createNewFile()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    JSONObject admin_obj = new JSONObject();
                    admin_obj.put("name", admin.getName());
                    admin_obj.put("mail", admin.getMail());
                    admin_obj.put("save_date", Main.formatDate(LocalDateTime.now()));

                    writer.write(this.getEnhancedJSONString(admin_obj));
                    writer.flush();
                }
            }
        }
    }

    public Optional<Administrator> getCurrentAdminInJSONFile() throws IOException, ParseException, SQLException {
        File file = new File(this.getSession_directory().getAbsolutePath() + "\\session_" + Main.formatDate(LocalDateTime.now()) + ".json");
        JSONParser parser = new JSONParser();

        if (file.exists()) {
            if (file.length() > 0) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    Object object = (Object) parser.parse(reader);
                    if (object != null) {
                        JSONObject admin_obj = (JSONObject) object;
                        String name = (String) admin_obj.get("name");
                        return Main.getAdministratorManager().getAdministrator(name);
                    } else {
                        return Optional.empty();
                    }
                }
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public void removeExistingSessionFile() throws IOException {
        File file = new File(this.getSession_directory().getAbsolutePath() + "\\session_" + Main.formatDate(LocalDateTime.now()) + ".json");
        if (file.exists()) {
            if (file.delete());
        }
    }
    
    public boolean existsSessionFile() {
        File file = new File(this.getSession_directory().getAbsolutePath() + "\\session_" + Main.formatDate(LocalDateTime.now()) + ".json");
        return file.exists();
    }

    private String getEnhancedJSONString(JSONObject object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(object);
    }

}
