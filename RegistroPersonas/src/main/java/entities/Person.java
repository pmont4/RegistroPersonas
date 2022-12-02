package entities;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@ToString
public class Person {
    
    private int id;
    private String name;
    private LocalDateTime birth_date;
    private String height;
    private char gender;

    public Person() {
    }

    public Person(@NotNull int id, @NotNull String name, @NotNull LocalDateTime birth_date, @Nullable String height, @Nullable char gender) {
        this.id = id;
        this.name = name;
        this.birth_date = birth_date;
        this.height = height;
        this.gender = gender;
    }
    
}
