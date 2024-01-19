package by.moiseenko.javataskplanner.controller;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import by.moiseenko.javataskplanner.domain.task.Task;
import by.moiseenko.javataskplanner.domain.user.User;
import by.moiseenko.javataskplanner.dto.task.TaskDto;
import by.moiseenko.javataskplanner.dto.user.UserDto;
import by.moiseenko.javataskplanner.dto.validation.OnCreate;
import by.moiseenko.javataskplanner.dto.validation.OnUpdate;
import by.moiseenko.javataskplanner.mapper.TaskMapper;
import by.moiseenko.javataskplanner.mapper.UserMapper;
import by.moiseenko.javataskplanner.service.TaskService;
import by.moiseenko.javataskplanner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @PutMapping
    public ResponseEntity<UserDto> update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.update(user);

        return new ResponseEntity<>(
                userMapper.toDto(updatedUser),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long id) {
        User user = userService.getById(id);

        return new ResponseEntity<>(
                userMapper.toDto(user),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<TaskDto>> getAllTasksByUser(@PathVariable("userId") Long id) {
        List<Task> allTasksByUser = taskService.getAllByUserId(id);

        return new ResponseEntity<>(
                taskMapper.toDto(allTasksByUser),
                HttpStatus.OK
        );
    }

    @PostMapping("/{userId}/task")
    public ResponseEntity<TaskDto> createNewTask(@PathVariable("userId") Long id,
                                                 @Validated(OnCreate.class) @RequestBody TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task createdTask = taskService.create(task, id);

        return new ResponseEntity<>(
                taskMapper.toDto(createdTask),
                HttpStatus.OK
        );
    }
}
