package by.moiseenko.javataskplanner.repository.mapper;

/*
    @author Ilya Moiseenko on 23.01.24
*/

import by.moiseenko.javataskplanner.domain.task.Task;
import by.moiseenko.javataskplanner.domain.user.Role;
import by.moiseenko.javataskplanner.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserRowMapper {

    private final RoleMapper roleMapper;
    private final TaskRowMapper taskRowMapper;

    public User mapRow(ResultSet resultSet) throws SQLException {
        Set<Role> roles = roleMapper.mapRows(resultSet);
        resultSet.beforeFirst();

        List<Task> tasks = taskRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();

        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("user_name"));
            user.setUsername(resultSet.getString("user_username"));
            user.setPassword(resultSet.getString("user_password"));
            user.setRoles(roles);
            user.setTasks(tasks);

            return user;
        }

        return null;
    }
}
