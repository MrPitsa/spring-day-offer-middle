package com.onedayoffer.taskdistribution.controllers;

import com.onedayoffer.taskdistribution.dto.EmployeeDTO;
import com.onedayoffer.taskdistribution.dto.TaskDTO;
import com.onedayoffer.taskdistribution.dto.TaskStatus;
import com.onedayoffer.taskdistribution.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RestController
@RequestMapping(path = "employees")
@AllArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDTO> getEmployees(@RequestParam(required = false) String sort) {
        log.debug("getEmployees: sort={}", sort);
        return employeeService.getEmployees(sort);
    }

    @GetMapping("{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO getOneEmployee(@PathVariable Integer employeeId) {
        log.debug("getOneEmployee: employeeId={}", employeeId);
        return employeeService.getOneEmployee(employeeId);
    }

    @GetMapping("{employeeId}/tasks")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDTO> getTasksByEmployeeId(@PathVariable Integer employeeId) {
        log.debug("getTasksByEmployeeId: employeeId={}", employeeId);
        return employeeService.getTasksByEmployeeId(employeeId);
    }

    @PatchMapping("{employeeId}/tasks/{taskId}/status")
    @ResponseStatus(HttpStatus.OK)
    public void changeTaskStatus(@PathVariable Integer employeeId,
                                 @PathVariable Integer taskId,
                                 @RequestParam TaskStatus newStatus) {
        log.debug("changeTaskStatus: employeeId={}, taskId={}, newStatus={}", employeeId, taskId, newStatus);
        employeeService.changeTaskStatus(employeeId, taskId, newStatus);
    }

    @PostMapping("{employeeId}/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO postNewTask(@PathVariable Integer employeeId,
                            @Valid @RequestBody TaskDTO taskDTO) {
        return employeeService.postNewTask(employeeId, taskDTO);
    }
}