package by.moiseenko.javataskplanner.repository;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import by.moiseenko.javataskplanner.domain.user.Role;
import by.moiseenko.javataskplanner.domain.user.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    void update(User user);
    void save(User user);
    void saveUserRole(Long userId, Role role);
    boolean idTaskOwner(Long userId, Long taskId);
    void removeById(Long id);
}
