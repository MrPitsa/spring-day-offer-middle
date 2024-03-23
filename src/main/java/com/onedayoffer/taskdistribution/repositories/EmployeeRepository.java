package com.onedayoffer.taskdistribution.repositories;

import com.onedayoffer.taskdistribution.repositories.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
