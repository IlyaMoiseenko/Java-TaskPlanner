package by.moiseenko.javataskplanner.mapper;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import by.moiseenko.javataskplanner.domain.task.Task;
import by.moiseenko.javataskplanner.dto.task.TaskDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto toDto(Task task);
    List<TaskDto> toDto(List<Task> tasks);
    Task toEntity(TaskDto taskDto);
}
