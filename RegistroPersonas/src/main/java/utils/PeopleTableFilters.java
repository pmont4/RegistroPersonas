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
                    case "adult":{
                        if (map.getValue() >= 18) {
                            toReturn.add(map.getKey());
                        }
                        break;
                    }
                    case "young":{
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
}
