package by.moiseenko.javataskplanner.repository.mapper;

/*
    @author Ilya Moiseenko on 22.01.24
*/

import by.moiseenko.javataskplanner.domain.task.Status;
import by.moiseenko.javataskplanner.domain.task.Task;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaskRowMapper {

    @SneakyThrows
    public Task mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("task_id"));
            task.setTitle(resultSet.getString("task_title"));
            task.setDescription(resultSet.getString("task_description"));
            task.setStatus(Status.valueOf(resultSet.getString("task_status")));

            Timestamp expirationDate = resultSet.getTimestamp("task_expiration_date");
            if (expirationDate != null) {
                task.setExpirationDate(expirationDate.toLocalDateTime());
            }

            return task;
        }

        return null;
    }

    @SneakyThrows
    public List<Task> mapRows(ResultSet resultSet) {
        List<Task> taskList = new ArrayList<>();

        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("task_id"));
            task.setTitle(resultSet.getString("task_title"));
            task.setDescription(resultSet.getString("task_description"));
            task.setStatus(Status.valueOf(resultSet.getString("task_status")));

            Timestamp expirationDate = resultSet.getTimestamp("task_expiration_date");
            if (expirationDate != null) {
                task.setExpirationDate(expirationDate.toLocalDateTime());
            }

            taskList.add(task);
        }

        return taskList;
    }
}
