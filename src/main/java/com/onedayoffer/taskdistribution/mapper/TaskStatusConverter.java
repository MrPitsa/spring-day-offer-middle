package com.onedayoffer.taskdistribution.mapper;

import com.onedayoffer.taskdistribution.dto.TaskStatus;
import com.onedayoffer.taskdistribution.exceptions.IllegalTaskStatusException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskStatusConverter implements Converter<String, TaskStatus> {

    @Override
    public TaskStatus convert(String status) {
        try {
            return TaskStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalTaskStatusException("Unknown task status: " + status);
        }
    }
}
