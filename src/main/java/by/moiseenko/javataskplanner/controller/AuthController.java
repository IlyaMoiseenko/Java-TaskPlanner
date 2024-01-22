package by.moiseenko.javataskplanner.controller;

/*
    @author Ilya Moiseenko on 22.01.24
*/

import by.moiseenko.javataskplanner.domain.user.User;
import by.moiseenko.javataskplanner.dto.request.AuthenticationRequest;
import by.moiseenko.javataskplanner.dto.response.AuthenticationResponse;
import by.moiseenko.javataskplanner.dto.user.UserDto;
import by.moiseenko.javataskplanner.dto.validation.OnCreate;
import by.moiseenko.javataskplanner.mapper.UserMapper;
import by.moiseenko.javataskplanner.service.AuthenticationService;
import by.moiseenko.javataskplanner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Validated @RequestBody AuthenticationRequest request) {
        return new ResponseEntity<>(
                authenticationService.login(request),
                HttpStatus.OK
        );
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);

        return new ResponseEntity<>(
                userMapper.toDto(createdUser),
                HttpStatus.OK
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody String refreshToken) {
        return new ResponseEntity<>(
                authenticationService.refresh(refreshToken),
                HttpStatus.OK
        );
    }
}
