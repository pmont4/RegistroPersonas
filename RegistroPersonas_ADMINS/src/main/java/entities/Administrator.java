package entities;

import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@ToString
public class Administrator {
    
    private int id;
    private String name;
    private String mail;
    private String password;
    private String address;
    
    private List<String> perms;
    
    private String last_session;

    public Administrator() {
    }

    public Administrator(@NotNull int id, @NotNull String name, @NotNull String mail, @NotNull String password, @Nullable String address, @NotNull List<String> perms, @NotNull String last_session) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.address = address;
        this.perms = perms;
        this.last_session = last_session;
    }
    
}
