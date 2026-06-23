package com.CrudDemoApplication.repo;

import com.CrudDemoApplication.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employee,Integer>{

}
