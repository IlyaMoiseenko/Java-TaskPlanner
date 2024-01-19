package by.moiseenko.javataskplanner.dto.task;

/*
    @author Ilya Moiseenko on 19.01.24
*/

import by.moiseenko.javataskplanner.domain.task.Status;
import by.moiseenko.javataskplanner.dto.validation.OnCreate;
import by.moiseenko.javataskplanner.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class TaskDto {

    @NotNull(message = "Id must be not null", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Title must be not null", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255, message = "Title length must be smaller than 255 symbols", groups = {OnUpdate.class, OnCreate.class})
    private String title;

    @Length(max = 255, message = "Title length must be smaller than 255 symbols", groups = {OnUpdate.class, OnCreate.class})
    private String description;

    @NotNull(message = "Status must be not null", groups = {OnCreate.class, OnUpdate.class})
    private Status status;

    @NotNull(message = "Date must be not null", groups = {OnCreate.class})
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime expirationDate;
}
