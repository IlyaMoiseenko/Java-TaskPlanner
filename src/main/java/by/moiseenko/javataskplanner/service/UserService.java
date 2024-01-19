package by.moiseenko.javataskplanner.service;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import by.moiseenko.javataskplanner.domain.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getById(Long id) {
        return null;
    }

    public User getByUsername(String username) {
        return null;
    }

    public User update(User user) {
        return null;
    }

    public User create(User user) {
        return null;
    }

    public boolean isTaskOwner(Long userId, Long taskId) {
        return false;
    }

    public void deleteById(Long id) {

    }

}
