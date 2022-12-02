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
    
    private String name;
    private String mail;
    private String password;
    private String address;
    
    private List<String> perms;
    private List<String> logger;
    private LocalDateTime last_session;

    public Administrator() {
    }

    public Administrator(@NotNull String name, @NotNull String mail, @NotNull String password, @Nullable String address, @NotNull List<String> perms, @NotNull List<String> logger, @NotNull LocalDateTime last_session) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.address = address;
        this.perms = perms;
        this.logger = logger;
        this.last_session = last_session;
    }
    
}
