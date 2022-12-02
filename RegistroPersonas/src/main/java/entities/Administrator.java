package entities;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@ToString
public class Administrator {
    
    @NotNull private String name;
    @NotNull private String mail;
    @NotNull private String password;
    @Nullable private String address;
    
    private List<String> perms;
    private List<String> logger;
    private LocalDateTime last_session;

    public Administrator() {
    }

    public Administrator(String name, String mail, String password, String address, List<String> perms, List<String> logger, LocalDateTime last_session) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.address = address;
        this.perms = perms;
        this.logger = logger;
        this.last_session = last_session;
    }
    
}
