package by.moiseenko.javataskplanner.service;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import by.moiseenko.javataskplanner.domain.user.Role;
import by.moiseenko.javataskplanner.domain.user.User;
import by.moiseenko.javataskplanner.exception.ResourceNotFoundException;
import by.moiseenko.javataskplanner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User was not found"));
    }

    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User was not found"));
    }


    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.update(user);

        return user;
    }

    public User create(User user) {
        if (isExists(user))
            throw new IllegalStateException("User exists");

        if (!isPasswordEquals(user.getPassword(), user.getPasswordConfirmation()))
            throw new IllegalStateException("Passwords do not match");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(Role.ROLE_USER));
        userRepository.save(user);

        return user;
    }

    @Transactional(readOnly = true)
    public boolean isTaskOwner(Long userId, Long taskId) {
        return userRepository.isTaskOwner(userId, taskId);
    }

    public void deleteById(Long id) {
        userRepository.removeById(id);
    }

    private boolean isExists(User user) {
        return userRepository.findByUsername(user.getUsername()).isPresent();
    }

    private boolean isPasswordEquals(String password, String passwordConfirmation) {
        return password.equals(passwordConfirmation);
    }

}
