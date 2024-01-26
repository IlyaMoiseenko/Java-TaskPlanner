package by.moiseenko.javataskplanner.domain.task;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Task implements Serializable {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime expirationDate;
}
