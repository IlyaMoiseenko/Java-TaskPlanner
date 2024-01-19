package by.moiseenko.javataskplanner.controller;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import by.moiseenko.javataskplanner.domain.task.Task;
import by.moiseenko.javataskplanner.dto.task.TaskDto;
import by.moiseenko.javataskplanner.dto.validation.OnUpdate;
import by.moiseenko.javataskplanner.mapper.TaskMapper;
import by.moiseenko.javataskplanner.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PutMapping
    public ResponseEntity<TaskDto> update(@Validated(OnUpdate.class) @RequestBody TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task updatedTask = taskService.update(task);

        return new ResponseEntity<>(
                taskMapper.toDto(updatedTask),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getById(@PathVariable("id") Long id) {
        Task task = taskService.getById(id);

        return new ResponseEntity<>(
                taskMapper.toDto(task),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        taskService.deleteById(id);
    }
}
