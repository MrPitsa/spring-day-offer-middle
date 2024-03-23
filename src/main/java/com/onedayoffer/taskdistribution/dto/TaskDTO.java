package com.onedayoffer.taskdistribution.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Integer id;
    @NotNull
    @NotBlank
    private String name;
    private TaskType taskType;
    private TaskStatus status;
    private Integer priority;
    private Integer leadTime;
}
