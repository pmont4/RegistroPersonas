package manager;

import entities.Person;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import main.Main;

public class PersonManager {

    @Getter
    private List<Person> person_list;

    public PersonManager() {
        try {
            this.person_list = new ArrayList<>();

            init();
            System.out.println(this.getPerson_list());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Optional<Person> getPerson(String name) {
        if (!this.getPerson_list().isEmpty()) {
            for (Person p : this.getPerson_list()) {
                if (p.getName().equalsIgnoreCase(name)) {
                    return Optional.of(p);
                }
            }
        } else {
            return Optional.empty();
        }
        return Optional.empty();
    }

    public void createPerson(String name, String birth_date, String height, char gender) throws SQLException {
        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("INSERT INTO people (id, name, birth_date, height, gender) VALUES (?,?,?,?,?)")) {
            stmt.setInt(1, 0);
            stmt.setString(2, name);
            stmt.setString(3, birth_date);
            stmt.setString(4, height);
            stmt.setString(5, String.valueOf(gender));
            stmt.execute();

            Person person = new Person(0, name, birth_date, height, gender);

            this.getPerson_list().add(person);
        }
    }

    public boolean deletePerson(int id) throws SQLException {
        PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM people WHERE id=?");
        stmt.setInt(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Optional<Person> opt = this.getPerson(rs.getString("name"));
                if (opt.isPresent()) {
                    Person person = opt.get();
                    stmt = Main.getMySQLConnection().prepareStatement("DELETE FROM people WHERE id=?");
                    stmt.setInt(1, person.getId());
                    stmt.execute();
                    stmt.close();
                    this.getPerson_list().remove(person);
                }
                return opt.isPresent();
            }
        }
        
        return false;
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
        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM people")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Person person = new Person(rs.getInt("id"), rs.getString("name"), rs.getString("birth_date"), rs.getString("height"), rs.getString("gender").charAt(0));

                    this.getPerson_list().add(person);
                }
            }
        }
    }

}
