package manager;

import entities.Person;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;
import lombok.Getter;
import main.Main;

public class PersonManager {

    @Getter private LinkedList<Person> person_list;
    
    @Getter private HashMap<Person, Integer> ageMap;

    public PersonManager() {
        try {
            this.person_list = new LinkedList<>();
            this.ageMap = new HashMap<>();
            
            init();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Optional<Person> getPerson(String name) throws SQLException {
        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM persons WHERE name=?")) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    if (this.getPerson_list().size() > 0) {
                        for (Person p : this.getPerson_list()) {
                            if (p.getName().equals(rs.getString("name"))) {
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
    
    public void createPerson(String name, String birth_date, String height, char gender) throws SQLException {
        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("INSERT INTO persons (id, name, birth_date, height, gender) VALUES (?,?,?,?,?)")) {
            stmt.setInt(1, 0);
            stmt.setString(2, name);
            stmt.setString(3, birth_date);
            stmt.setString(4, height);
            stmt.setString(5, String.valueOf(gender));
            stmt.execute();
            
            
            Person person = new Person(0, name, birth_date, height, gender);
            
            this.getPerson_list().add(person);
            this.getAgeMap().put(person, this.getPersonAge(person));
        }
    }
    
    public void deletePerson(String name) throws SQLException {
        Optional<Person> person_o = this.getPerson(name);
        if (person_o.isPresent()) {
            Person person = person_o.get();
            try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("DELETE FROM persons WHERE name=?")) {
                stmt.setString(1, person.getName());
                stmt.executeUpdate();
                
                this.getPerson_list().remove(person);
                if (this.getAgeMap().containsKey(person)) {
                    this.getAgeMap().remove(person);
                }
            }
        }
    }
    
    public int getPersonAge(Person person) {
        String[] split = person.getBirth_date().split("-");
        
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        
        LocalDate birth_date = LocalDate.of(year, month, day);
        Period period = Period.between(birth_date, LocalDate.now());
        return period.getYears();
    }

    synchronized void init() throws SQLException {
        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM persons")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Person person = new Person(rs.getInt("id"), rs.getString("name"), rs.getString("birth_date"), rs.getString("height"), rs.getString("gender").charAt(0));
                    
                    this.getPerson_list().add(person);
                    this.getAgeMap().put(person, this.getPersonAge(person));
                }
            }
        }
    }

}
