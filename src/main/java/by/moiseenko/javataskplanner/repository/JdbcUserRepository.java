package by.moiseenko.javataskplanner.repository;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import by.moiseenko.javataskplanner.domain.user.Role;
import by.moiseenko.javataskplanner.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void save(User user) {

    }

    @Override
    public void saveUserRole(Long userId, Role role) {

    }

    @Override
    public boolean idTaskOwner(Long userId, Long taskId) {
        return false;
    }

    @Override
    public void removeById(Long id) {

    }

}
