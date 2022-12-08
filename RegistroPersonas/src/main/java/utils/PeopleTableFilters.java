package utils;

import entities.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import main.Main;

public class PeopleTableFilters {

    public PeopleTableFilters() {
    }

    public List<Person> filterAge(String age_limit) {
        List<Person> toReturn = new ArrayList<>();

        if (!Main.getPersonManager().getAgeMap().isEmpty()) {
            for (Map.Entry<Person, Integer> map : Main.getPersonManager().getAgeMap().entrySet()) {
                switch (age_limit) {
                    case "adult": {
                        if (map.getValue() >= 18) {
                            toReturn.add(map.getKey());
                        }
                        break;
                    }
                    case "young": {
                        if (map.getValue() < 18) {
                            toReturn.add(map.getKey());
                        }
                        break;
                    }
                }
            }
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
                                    if(height >= 5.10) {
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
                                    if(height < 5.10) {
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

        return toReturn;
    }
}
