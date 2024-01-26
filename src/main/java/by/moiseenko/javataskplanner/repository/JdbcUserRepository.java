package by.moiseenko.javataskplanner.repository;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import by.moiseenko.javataskplanner.config.DataSourceConfig;
import by.moiseenko.javataskplanner.domain.user.Role;
import by.moiseenko.javataskplanner.domain.user.User;
import by.moiseenko.javataskplanner.exception.ResourceMappingException;
import by.moiseenko.javataskplanner.exception.ResourceNotFoundException;
import by.moiseenko.javataskplanner.repository.mapper.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {

    private final DataSourceConfig dataSourceConfig;
    private final UserRowMapper userRowMapper;

    private final String FIND_BY_ID = """
            SELECT u.id as user_id,
                   u.name as user_name,
                   u.username as user_username,
                   u.password as user_password,
            
                   user_role.role as role,
            
                   task.id as task_id,
                   task.title as task_title,
                   task.description as task_description,
                   task.status as task_status,
                   task.expiration_date as task_expiration_date
            FROM tb_user u
            LEFT JOIN tb_user_role user_role on u.id = user_role.user_id
            LEFT JOIN tb_user_tb_task user_task on u.id = user_task.user_id
            LEFT JOIN tb_task task on user_task.task_id = task.id
            WHERE u.id = ?
            """;

    private final String FIND_BY_USERNAME = """
            SELECT u.id as user_id,
                   u.name as user_name,
                   u.username as user_username,
                   u.password as user_password,
            
                   user_role.role as user_role,
            
                   task.id as task_id,
                   task.title as task_title,
                   task.description as task_description,
                   task.status as task_status,
                   task.expiration_date as task_expiration_date
            FROM tb_user u
            LEFT JOIN tb_user_role user_role on u.id = user_role.user_id
            LEFT JOIN tb_user_tb_task user_task on u.id = user_task.user_id
            LEFT JOIN tb_task task on user_task.task_id = task.id
            WHERE u.username = ?
            """;

    private final String UPDATE_BY_ID = """
            UPDATE tb_user
            SET name = ?,
                username = ?,
                password = ?
            WHERE id = ?
            """;

    private final String SAVE = """
            INSERT INTO tb_user (name, username, password)
            VALUES (?, ?, ?)
            """;

    private final String SAVE_USER_ROLE = """
            INSERT INTO tb_user_role (user_id, role)
            VALUES (?, ?)
            """;

    private final String IS_TASK_OWNER = """
            SELECT exists(
                SELECT 1
                FROM tb_user_tb_task
                WHERE user_id = ?
                AND task_id = ?
            )
            """;

    private final String REMOVE_BY_ID = """
            DELETE FROM tb_user
            WHERE id = ?
            """;

    @Override
    public Optional<User> findById(Long id) {
        try (Connection connection = dataSourceConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return Optional.ofNullable(userRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new ResourceNotFoundException("User was not found");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (Connection connection = dataSourceConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    FIND_BY_USERNAME,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return Optional.ofNullable(userRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new ResourceNotFoundException("User was not found");
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = dataSourceConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while updating user");
        }
    }

    @Override
    public void save(User user) {
        try (Connection connection = dataSourceConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SAVE,
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next())
                    user.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while saving user");
        }
    }

    @Override
    public void saveUserRole(Long userId, Role role) {
        try (Connection connection = dataSourceConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_ROLE);
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, role.name());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while saving role of user");
        }
    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        try (Connection connection = dataSourceConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(IS_TASK_OWNER);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, taskId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while check is task owner");
        }
    }

    @Override
    public void removeById(Long id) {
        try (Connection connection = dataSourceConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BY_ID);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while deleting user");
        }
    }

}
