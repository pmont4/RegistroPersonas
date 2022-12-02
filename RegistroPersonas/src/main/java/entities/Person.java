package entities;

import lombok.Data;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@ToString
public class Person {
    
    @NotNull private int id;
    @NotNull private String name;
    @NotNull private int age;
    @Nullable private String height;
    @Nullable private char gender;

    public Person() {
    }

    public Person(int id, String name, int age, String height, char gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        this.gender = gender;
    }
    
}
