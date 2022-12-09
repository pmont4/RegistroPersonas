package utils;

import entities.Person;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.Main;

public class PeopleTableFilters {

    public PeopleTableFilters() {
    }
    
    public void removeFiltres() throws SQLException {
        List<Person> list = new ArrayList<>();
        
        try (PreparedStatement stmt = Main.getMySQLConnection().prepareStatement("SELECT * FROM people")) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Person p = new Person(rs.getInt("id"), rs.getString("name"), rs.getString("birth_date"), rs.getString("height"), rs.getString("gender").charAt(0));
                    list.add(p);
                }
            }
        }
        
        if (!list.isEmpty()) {
            Main.getPersonManager().getPerson_list().clear();
            Main.getPersonManager().getPerson_list().addAll(list);
        }
    }

    public List<Person> filterAge(String age_limit) {
        List<Person> toReturn = new ArrayList<>();

        Main.getPersonManager().getPerson_list().forEach(p -> {
            switch (age_limit) {
                case "adult": {
                    if (Main.getPersonManager().getPersonAge(p) >= 18) {
                        toReturn.add(p);
                    }
                    break;
                }
                case "young": {
                    if (Main.getPersonManager().getPersonAge(p) < 18) {
                        toReturn.add(p);
                    }
                    break;
                }
            }
        });
        
        if (!toReturn.isEmpty()) {
            Main.getPersonManager().getPerson_list().clear();
            Main.getPersonManager().getPerson_list().addAll(toReturn);
        }
        return toReturn;
    }

    public List<Person> filterHeightPeople(String option) {
        List<Person> toReturn = new ArrayList<>();

        if (!Main.getPersonManager().getPerson_list().isEmpty()) {
            for (Person p : Main.getPersonManager().getPerson_list()) {
                if (!p.getHeight().equals("None")) {
                    switch (option) {
                        case "taller": {
                            String[] split = p.getHeight().split("\\-");
                            switch (split[1]) {
                                case "cm": {
                                    int height = Integer.parseInt(split[0]);
                                    if (height >= 180) {
                                        toReturn.add(p);
                                    }
                                    break;
                                }
                                case "ft": {
                                    double height = Double.parseDouble(split[0]);
                                    if (height >= 5.90) {
                                        toReturn.add(p);
                                    }
                                    break;
                                }
                            }
                            break;
                        }
                        case "smaller": {
                            String[] split = p.getHeight().split("\\-");
                            switch (split[1]) {
                                case "cm": {
                                    int height = Integer.parseInt(split[0]);
                                    if (height < 180) {
                                        toReturn.add(p);
                                    }
                                    break;
                                }
                                case "ft": {
                                    double height = Double.parseDouble(split[0]);
                                    if (height < 5.90) {
                                        toReturn.add(p);
                                    }
                                    break;
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
        
        if (!toReturn.isEmpty()) {
            Main.getPersonManager().getPerson_list().clear();
            Main.getPersonManager().getPerson_list().addAll(toReturn);
        }
        return toReturn;
    }
}
