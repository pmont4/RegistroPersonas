package manager;

import entities.Person;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Optional;
import lombok.Getter;
import main.Main;

public class PersonManager {

    @Getter private LinkedList<Person> person_list;

    public PersonManager() {
        try {
            this.person_list = new LinkedList<>();
            init();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Optional<Person> getPerson(int id) throws SQLException {
        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM persons WHERE id=?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    if (this.getPerson_list().size() > 0) {
                        for (Person p : this.getPerson_list()) {
                            if (p.getId() == rs.getInt("id")) {
                                return Optional.of(p);
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
        }

        return Optional.empty();
    }
    
    public void createPerson(String name, int age, String height, char gender) throws SQLException {
        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("INSERT INTO persons (id, name, age, height, gender) VALUES (?,?,?,?,?)")) {
            stmt.setInt(1, 0);
            stmt.setString(2, name);
            stmt.setInt(3, age);
            stmt.setString(4, height);
            stmt.setString(5, String.valueOf(gender));
            stmt.execute();
            
            Person person = new Person(0, name, age, height, gender);
            this.getPerson_list().add(person);
        }
    }
    
    public void deletePerson(int id) throws SQLException {
        Optional<Person> person_o = this.getPerson(id);
        if (person_o.isPresent()) {
            Person person = person_o.get();
            try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("DELETE FROM persons WHERE id=?")) {
                stmt.setInt(1, person.getId());
                stmt.executeUpdate();
                this.getPerson_list().remove(person);
            }
        }
    }

    synchronized void init() throws SQLException {
        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM persons")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Person person = new Person(rs.getInt("id"), rs.getString("name"), rs.getInt("age"), rs.getString("height"), rs.getString("gender").charAt(0));
                    this.getPerson_list().add(person);
                }
            }
        }
    }

}
