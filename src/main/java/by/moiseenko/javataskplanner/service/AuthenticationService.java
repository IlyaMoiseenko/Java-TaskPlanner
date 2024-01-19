package by.moiseenko.javataskplanner.service;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import by.moiseenko.javataskplanner.dto.request.AuthenticationRequest;
import by.moiseenko.javataskplanner.dto.response.AuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public AuthenticationResponse login(AuthenticationRequest request) {
        return null;
    }

    public AuthenticationResponse refresh(String refreshToken) {
        return null;
    }
}
