package com.CrudDemoApplication.service;

import com.CrudDemoApplication.Entity.Employee;
import com.CrudDemoApplication.repo.EmployeeRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService
{

private final EmployeeRepo employeeRepo;

    public EmployeeServiceImpl(EmployeeRepo employeeRepo)
    {
        this.employeeRepo = employeeRepo;
    }


    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee findById(int theId) {
        Optional<Employee> result= employeeRepo.findById(theId);
        Employee theEmployee=null;
        if(result.isPresent()){
theEmployee=result.get();
        }
        else {
            throw new RuntimeException("id is not found --"+theId);
        }
        return theEmployee;
    }

    @Override
    public Employee save(Employee theEmployee) {
        return employeeRepo.save(theEmployee);
    }

    @Override
    public void deleteById(int theId) {
    employeeRepo.deleteById(theId);;
    }
}
