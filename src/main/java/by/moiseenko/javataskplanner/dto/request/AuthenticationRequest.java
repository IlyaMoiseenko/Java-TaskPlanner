package by.moiseenko.javataskplanner.dto.request;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;
    private String password;
}
