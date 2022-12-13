package manager;

import entities.Administrator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import lombok.Getter;
import main.Main;
import utils.Log;
import utils.NoSpecifiedPermsException;

public class AdministratorManager {

    @Getter
    private LinkedList<Administrator> administrator_list;

    public AdministratorManager() {
        try {
            this.administrator_list = new LinkedList<>();
            init();
            Log.write(this.getClass(), this.getAdministrator_list(), 1);
        } catch (Exception ex) {
            Log.write(this.getClass(), ex.getLocalizedMessage(), 3);
        }
    }

    public Optional<Administrator> getAdministrator(String name) {
        if (!this.getAdministrator_list().isEmpty()) {
            Iterator<Administrator> iterate = this.getAdministrator_list().iterator();
            while (iterate.hasNext()) {
                var a = iterate.next();
                if (a.getName().equalsIgnoreCase(name)) {
                    return Optional.of(a);
                }
            }
        } else {
            return Optional.empty();
        }
        return Optional.empty();
    }

    synchronized void init() throws Exception {
        try {
            try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM administrators")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String newPass = rs.getString("password").replaceAll(".", "*");
                        Administrator admin = new Administrator(rs.getString("name"), rs.getString("mail"), newPass, rs.getString("address"), new ArrayList<>());

                        if (rs.getString("perms").contains(",")) {
                            String[] split = rs.getString("perms").split(",");
                            boolean validStringPerms = false;
                            for (String split1 : split) {
                                if (split1.equals("add") || split1.equals("modify") || split1.equals("remove")) {
                                    validStringPerms = true;
                                } else {
                                    Log.write(this.getClass(), new NoSpecifiedPermsException("The current permission string does not contains any existing permission (Add, remove, modify), please verify the upcoming permission string and try again."));
                                }
                            }

                            if (validStringPerms) {
                                admin.setPerms(Arrays.asList(split));
                            }
                        } else {
                            if (rs.getString("perms").equals("add") || rs.getString("perms").equals("modify") || rs.getString("perms").equals("remove")) {
                                admin.setPerms(Arrays.asList(rs.getString("perms")));
                            } else {
                                Log.write(this.getClass(), new NoSpecifiedPermsException("The current permission string does not contains any existing permission (Add, remove, modify), please verify the upcoming permission string and try again."));
                            }
                        }

                        this.getAdministrator_list().add(admin);
                    }
                }
            }
        } catch (SQLException e) {
            Log.write(this.getClass(), e.getLocalizedMessage(), 3);
        }
    }

    public void submitLog(Administrator admin, String info) throws SQLException {
        if (Main.tableExists(admin.getName() + "_log")) {
            PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM " + admin.getName() + "_log WHERE date=?");
            stmt.setString(1, Main.formatDate(LocalDateTime.now()));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    if (!rs.getString("log").equals("")) {
                        StringBuilder log_builder = new StringBuilder();
                        String previous_log = rs.getString("log");
                        log_builder.append(previous_log).append(", ").append(info);

                        stmt = Main.getMySQLConnection().prepareStatement("UPDATE " + admin.getName() + "_log SET log=? WHERE date=?");
                        stmt.setString(1, log_builder.toString());
                        stmt.setString(2, Main.formatDate(LocalDateTime.now()));
                        stmt.executeUpdate();
                    }
                } else {
                    stmt = Main.getMySQLConnection().prepareStatement("INSERT INTO " + admin.getName() + "_log (date, log) VALUES (?,?)");
                    stmt.setString(1, Main.formatDate(LocalDateTime.now()));
                    stmt.setString(2, info);
                    stmt.execute();
                }
            }
        }
    }
    
}
