package by.moiseenko.javataskplanner.dto.response;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

    private String username;
    private String accessToken;
}
