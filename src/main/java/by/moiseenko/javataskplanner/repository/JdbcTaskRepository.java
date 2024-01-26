package by.moiseenko.javataskplanner.repository;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import by.moiseenko.javataskplanner.config.DataSourceConfig;
import by.moiseenko.javataskplanner.domain.task.Task;
import by.moiseenko.javataskplanner.exception.ResourceMappingException;
import by.moiseenko.javataskplanner.exception.ResourceNotFoundException;
import by.moiseenko.javataskplanner.repository.mapper.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcTaskRepository implements TaskRepository {

    private final DataSourceConfig dataSourceConfig;
    private final TaskRowMapper taskRowMapper;

    private final String FIND_BY_ID = """
            SELECT task.id as task_id,
                   task.title as task_title,
                   task.description as task_description,
                   task.status as task_status,
                   task.expiration_date as task_expiration_date
            FROM tb_task task
            WHERE task.id = ?
            """;

    private final String FIND_ALL_BY_USER_ID = """
            SELECT *
            FROM tb_task task
            JOIN tb_user_tb_task user_task on task.id = user_task.task_id
            WHERE user_task.user_id = ?
            """;

    private final String ASSIGN = """
            INSERT INTO tb_user_tb_task (user_id, task_id)
            VALUES (?, ?)
            """;

    private final String DELETE = """
            DELETE FROM tb_task
            WHERE id = ?
            """;

    private final String UPDATE_BY_ID = """
            UPDATE tb_task
            SET title = ?,
                description = ?,
                expiration_date = ?,
                status = ?
            WHERE id = ?
            """;

    private final String SAVE = """
            INSERT INTO tb_task (title, description, status, expiration_date)
            VALUES (?, ?, ?, ?)
            """;

    @Override
    public Optional<Task> findById(Long id) {
        try (Connection connection = dataSourceConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return Optional.ofNullable(taskRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw  new ResourceNotFoundException("User with id: " + id + " was not found");
        }
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        try (Connection connection = dataSourceConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            preparedStatement.setLong(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return taskRowMapper.mapRows(resultSet);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while mapping rows");
        }
    }

    @Override
    public void assignToUserById(Long taskId, Long userId) {
        try (Connection connection = dataSourceConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ASSIGN);
            preparedStatement.setLong(1, taskId);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while assigning to task");
        }
    }

    @Override
    public void update(Task task) {
        try (Connection connection = dataSourceConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
            preparedStatement.setString(1, task.getTitle());

            if (task.getDescription() == null)
                preparedStatement.setNull(2, Types.VARCHAR);
            else
                preparedStatement.setString(2, task.getDescription());

            preparedStatement.setString(3, task.getStatus().name());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(task.getExpirationDate()));
            preparedStatement.setLong(5, task.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while updating task");
        }
    }

    @Override
    public void save(Task task) {
        try (Connection connection = dataSourceConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, task.getTitle());

            if (task.getDescription() == null)
                preparedStatement.setNull(2, Types.VARCHAR);
            else
                preparedStatement.setString(2, task.getDescription());

            preparedStatement.setString(3, task.getStatus().name());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(task.getExpirationDate()));

            preparedStatement.executeUpdate();

            try (ResultSet key = preparedStatement.getGeneratedKeys()) {
                task.setId(key.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while creating task");
        }
    }

    @Override
    public void removeById(Long id) {
        try (Connection connection = dataSourceConfig.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while deleting task");
        }
    }

}
