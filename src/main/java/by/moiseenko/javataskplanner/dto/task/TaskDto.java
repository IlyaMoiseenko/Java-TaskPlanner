package by.moiseenko.javataskplanner.dto.task;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import by.moiseenko.javataskplanner.domain.task.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime expirationDate;
}
