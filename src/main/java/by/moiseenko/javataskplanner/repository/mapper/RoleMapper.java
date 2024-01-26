package by.moiseenko.javataskplanner.repository.mapper;

/*
    @author Ilya Moiseenko on 23.01.24
*/

import by.moiseenko.javataskplanner.domain.user.Role;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class RoleMapper {

    public Role mapRow(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Role.valueOf(resultSet.getString("role"));
        }

        return null;
    }

    public Set<Role> mapRows(ResultSet resultSet) throws SQLException {
        Set<Role> roles = new HashSet<>();

        while (resultSet.next()) {
            roles.add(Role.valueOf(resultSet.getString("role")));
        }

        return roles;
    }
}
