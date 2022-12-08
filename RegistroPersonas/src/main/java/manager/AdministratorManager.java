package manager;

import entities.Administrator;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import lombok.Getter;
import main.Main;
import utils.NoSpecifiedPermsException;

public class AdministratorManager {

    @Getter private LinkedList<Administrator> administrator_list;

    public AdministratorManager() {
        try {
            this.administrator_list = new LinkedList<>();
            init();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Optional<Administrator> getAdministrator(String name) {
        if (!this.getAdministrator_list().isEmpty()) {
            for (Administrator a : this.getAdministrator_list()) {
                if (a.getName().equalsIgnoreCase(name)) {
                    return Optional.of(a);
                }
            }
        } else {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public void createAdministrator(String name, String mail, String password, String address, String perms) throws Exception {
        Administrator admin = new Administrator();
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
                    throw new NoSpecifiedPermsException("The current permission string does not contains any existing permission (Add, remove, modify), please verify the upcoming permission string and try again.");
                }
            }
        
            if (validStringPerms) {
                admin.setPerms(Arrays.asList(split));
            }
        } else {
            if (perms.equals("add") || perms.equals("modify") || perms.equals("remove")) {
                admin.setPerms(Arrays.asList(perms));
            } else {
                throw new NoSpecifiedPermsException("The current permission string does not contains any existing permission (Add, remove, modify), please verify the upcoming permission string and try again.");
            }
        }
        
        admin.setLogger(new ArrayList<>());
        
        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("INSERT INTO administrators (name, mail, password, address, perms) VALUES (?,?,?,?,?)")) {
            stmt.setString(1, admin.getName());
            stmt.setString(2, admin.getMail());
            stmt.setString(3, admin.getPassword());
            stmt.setString(4, admin.getAddress());
            stmt.setString(5, perms);
            stmt.execute();
        }
        
        this.getAdministrator_list().add(admin);
    }

    synchronized void init() throws Exception {
        try {
            PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM administrators");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String newPass = rs.getString("password").replaceAll(".", "*");
                    Administrator admin = new Administrator(rs.getString("name"), rs.getString("mail"), newPass, rs.getString("address"), new ArrayList<>(), new ArrayList<>());

                    if (rs.getString("perms").contains(",")) {
                        String[] split = rs.getString("perms").split(",");
                        boolean validStringPerms = false;
                        for (String split1 : split) {
                            if (split1.equals("add") || split1.equals("modify") || split1.equals("remove")) {
                                validStringPerms = true;
                            } else {
                                throw new NoSpecifiedPermsException("The current permission string does not contains any existing permission (Add, remove, modify), please verify the upcoming permission string and try again.");
                            }
                        }

                        if (validStringPerms) {
                            admin.setPerms(Arrays.asList(split));
                        }
                    } else {
                        if (rs.getString("perms").equals("add") || rs.getString("perms").equals("modify") || rs.getString("perms").equals("remove")) {
                            admin.setPerms(Arrays.asList(rs.getString("perms")));
                        } else {
                            throw new NoSpecifiedPermsException("The current permission string does not contains any existing permission (Add, remove, modify), please verify the upcoming permission string and try again.");
                        }
                    }

                    this.getAdministrator_list().add(admin);
                }
            }

            if (this.getAdministrator_list().size() > 0) {
                for (Administrator a : this.getAdministrator_list()) {
                    try {
                        stmt = Main.getMySQLConnection().prepareStatement("CREATE TABLE IF NOT EXISTS " + a.getName() + "_log (date VARCHAR(45) NOT NULL, log LONGTEXT, PRIMARY KEY(date))");
                        stmt.execute();
                        stmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    this.updateLogAdmin(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateLogAdmin(Administrator admin) throws SQLException {
        if (this.tableExists(admin.getName() + "_log")) {
            try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM " + admin.getName() + "_log WHERE date=?")) {
                stmt.setString(1, Main.formatDate(LocalDateTime.now()));
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        if (rs.getString("log").contains(",")) {
                            String[] split = rs.getString("log").split(",");
                            admin.setLogger(Arrays.asList(split));
                        } else {
                            admin.setLogger(Arrays.asList(rs.getString("log")));
                        }
                    } else {
                        admin.setLogger(new ArrayList<>());
                    }
                }
            }
        }
    }
    
    private boolean tableExists(String table) throws SQLException {
        boolean exists = false;
        try (ResultSet rs = Main.getMySQLConnection().getMetaData().getTables(null, null, table, null)) {
            while (rs.next()) {
                String name = rs.getString("TABLE_NAME");
                if (name != null && name.equals(table.toLowerCase())) {
                    exists = true;
                    break;
                }
            }
        }
        return exists;
    }

}
