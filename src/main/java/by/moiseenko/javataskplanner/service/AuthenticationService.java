package by.moiseenko.javataskplanner.service;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import by.moiseenko.javataskplanner.config.security.JwtService;
import by.moiseenko.javataskplanner.config.security.domain.UserPrincipal;
import by.moiseenko.javataskplanner.domain.user.User;
import by.moiseenko.javataskplanner.dto.request.AuthenticationRequest;
import by.moiseenko.javataskplanner.dto.response.AuthenticationResponse;
import by.moiseenko.javataskplanner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private String token;

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        Optional<User> userByEmail = userRepository.findByUsername(request.getUsername());
        if (userByEmail.isPresent()) {
            User user = userByEmail.get();

            UserDetails userPrincipal = createUserPrincipal(user);
            token = jwtService.generateToken(userPrincipal);
        }

        return AuthenticationResponse
                .builder()
                .username(request.getUsername())
                .accessToken(token)
                .build();
    }

    public AuthenticationResponse refresh(String refreshToken) {
        return null;
    }

    private UserDetails createUserPrincipal(User user) {
        return UserPrincipal
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(UserPrincipal.mapRolesToGrantedAuthority(user.getRoles()))
                .build();
    }
}
