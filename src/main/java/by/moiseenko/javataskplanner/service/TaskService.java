package by.moiseenko.javataskplanner.service;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import by.moiseenko.javataskplanner.domain.task.Status;
import by.moiseenko.javataskplanner.domain.task.Task;
import by.moiseenko.javataskplanner.exception.ResourceNotFoundException;
import by.moiseenko.javataskplanner.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public Task getById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task was not found"));
    }

    @Transactional(readOnly = true)
    public List<Task> getAllByUserId(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }

    public Task update(Task task) {
        if (task.getStatus() == null)
            task.setStatus(Status.TODO);

        taskRepository.update(task);

        return task;
    }

    public Task create(Task task, Long ownerId) {
        task.setStatus(Status.TODO);
        taskRepository.save(task);
        taskRepository.assignToUserById(task.getId(), ownerId);

        return task;
    }

    public void deleteById(Long id) {
        taskRepository.removeById(id);
    }
}
