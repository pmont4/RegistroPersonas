package manager;

import entities.Administrator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
        } catch (Exception ex) {
            Log.write(this.getClass(), ex.getLocalizedMessage(), 3);
        }
    }

    public Optional<Administrator> getAdministrator(String name) {
        if (!this.getAdministrator_list().isEmpty()) {
            return this.administrator_list.stream().filter(a -> a.getName().equals(name)).findFirst();
        } else {
            return Optional.empty();
        }
    }

    public void createAdministrator(String name, String mail, String password, String address, String perms) throws Exception {
        Administrator admin = new Administrator();
        admin.setId(0);
        admin.setName(name);
        admin.setMail(mail);
        admin.setPassword(password);
        admin.setAddress(address);

        if (perms.contains(",")) {
            String[] split = perms.split(",");
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
            if (perms.equals("add") || perms.equals("modify") || perms.equals("remove")) {
                admin.setPerms(Arrays.asList(perms));
            } else {
                Log.write(this.getClass(), new NoSpecifiedPermsException("The current permission string does not contains any existing permission (Add, remove, modify), please verify the upcoming permission string and try again."));
            }
        }

        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("INSERT INTO administrators (id, name, mail, password, address, perms) VALUES (?,?,?,?,?,?)")) {
            stmt.setInt(1, 0);
            stmt.setString(2, admin.getName());
            stmt.setString(3, admin.getMail());
            stmt.setString(4, admin.getPassword());
            stmt.setString(5, admin.getAddress());
            stmt.setString(6, perms);
            stmt.execute();
        }

        this.getAdministrator_list().add(admin);
    }

    public boolean deleteAdministrator(String name) throws SQLException {
        PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM administrators WHERE id=?");
        stmt.setString(1, name);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Optional<Administrator> opt = this.getAdministrator(name);
                if (opt.isPresent()) {
                    Administrator admin = opt.get();
                    stmt = Main.getMySQLConnection().prepareStatement("DELETE FROM administrators WHERE name=?");
                    stmt.setString(1, admin.getName());
                    stmt.execute();
                    stmt = Main.getMySQLConnection().prepareStatement("DROP TABLE " + admin.getName() + "_log");
                    stmt.execute();
                    stmt.close();

                    this.getAdministrator_list().remove(admin);
                    return true;
                }
            }
        }
        return false;
    }

    protected synchronized void init() throws Exception {
        try {
            try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM administrators")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String newPass = rs.getString("password").replaceAll(".", "*");
                        Administrator admin = new Administrator(rs.getInt("id"), rs.getString("name"), rs.getString("mail"), newPass, rs.getString("address"), new ArrayList<>(), "");
                        
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
                this.getAdministrator_list().forEach(a -> {
                    try {
                        this.updateLastSessionDate(a);
                    } catch (SQLException ex) {
                        Log.write(this.getClass(), ex.getLocalizedMessage(), 3);
                    }
                });
            }
        } catch (SQLException e) {
            Log.write(this.getClass(), e.getLocalizedMessage(), 3);
        }
    }

    public void updateLastSessionDate(Administrator admin) throws SQLException {
        if (Main.tableExists(admin.getName() + "_log")) {
            String table_name = admin.getName() + "_log";
            try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT date FROM " + table_name + " ORDER BY date DESC LIMIT 1")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        admin.setLast_session(rs.getString("date"));
                    } else {
                        admin.setLast_session("None");
                    }
                }
            }
        }
    }

}
