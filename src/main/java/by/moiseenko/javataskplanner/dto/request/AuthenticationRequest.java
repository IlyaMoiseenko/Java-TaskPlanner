package by.moiseenko.javataskplanner.dto.request;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import by.moiseenko.javataskplanner.dto.validation.OnCreate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthenticationRequest {

    @NotNull(message = "Username must be not null", groups = OnCreate.class)
    private String username;

    @NotNull(message = "Password must be not null", groups = OnCreate.class)
    private String password;
}
