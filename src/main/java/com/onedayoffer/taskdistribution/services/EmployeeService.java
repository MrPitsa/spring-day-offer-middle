package com.onedayoffer.taskdistribution.services;

import com.onedayoffer.taskdistribution.dto.EmployeeDTO;
import com.onedayoffer.taskdistribution.dto.TaskDTO;
import com.onedayoffer.taskdistribution.dto.TaskStatus;
import com.onedayoffer.taskdistribution.exceptions.DataNotFoundException;
import com.onedayoffer.taskdistribution.repositories.EmployeeRepository;
import com.onedayoffer.taskdistribution.repositories.TaskRepository;
import com.onedayoffer.taskdistribution.repositories.entities.Employee;
import com.onedayoffer.taskdistribution.repositories.entities.Task;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private static final String EMPLOYEE_NOT_FOUND = "Employee %d not found";
    private static final String EMPLOYEE_TASK_NOT_FOUND = "Task %d for employee %d not found or unavailable";

    public List<EmployeeDTO> getEmployees(@Nullable String sortDirectionName) {
        Sort.Direction sortDirection = Sort.Direction.DESC;
        if (sortDirectionName != null) {
            sortDirection = Sort.Direction.valueOf(sortDirectionName.toUpperCase());
        }
        List<Employee> employees = employeeRepository.findAll(Sort.by(sortDirection, "fio"));
        Type listType = new TypeToken<List<EmployeeDTO>>(){}.getType();
        return modelMapper.map(employees, listType);
    }

    @Transactional
    public EmployeeDTO getOneEmployee(Integer employeeId) {
        Employee employee = findEmployeeById(employeeId);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public List<TaskDTO> getTasksByEmployeeId(Integer employeeId) {
        List<Task> employeeTasks = taskRepository.findAllByEmployeeId(employeeId);
        Type listType = new TypeToken<List<TaskDTO>>() {}.getType();
        return modelMapper.map(employeeTasks, listType);
    }

    @Transactional
    public void changeTaskStatus(Integer employeeId, Integer taskId, TaskStatus status) {
        findEmployeeById(employeeId);

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new DataNotFoundException(EMPLOYEE_TASK_NOT_FOUND.formatted(taskId, employeeId)));

        if (!employeeId.equals(task.getEmployee().getId())) {
            throw new DataNotFoundException(EMPLOYEE_TASK_NOT_FOUND.formatted(taskId, employeeId));
        }

        task.setStatus(status);
        taskRepository.save(task);
    }

    @Transactional
    public TaskDTO postNewTask(Integer employeeId, TaskDTO newTask) {
        Employee employee = findEmployeeById(employeeId);
        Task task = modelMapper.map(newTask, Task.class);
        task.setEmployee(employee);
        taskRepository.save(task);
        return modelMapper.map(task, TaskDTO.class);
    }

    private Employee findEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new DataNotFoundException(EMPLOYEE_NOT_FOUND.formatted(employeeId)));
    }
}
