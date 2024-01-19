package by.moiseenko.javataskplanner.dto.user;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String username;
    private String password;
    private String passwordConfirmation;
}
