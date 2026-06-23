package com.CrudDemoApplication.rest;

import com.CrudDemoApplication.Entity.Employee;
import com.CrudDemoApplication.service.EmployeeService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;


//quick and dirty inject employeeDao use constructor injection also inject jsonMapper
    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService, ObjectMapper objectMapper)
    {
      this.  employeeService = theEmployeeService;

        this.objectMapper = objectMapper;
    }
    @GetMapping("/employee")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }

    //get the employee by id
    @GetMapping("/employee/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId){
        Employee theEmployee=employeeService.findById(employeeId);
        if(theEmployee==null){
            throw new RuntimeException("Employee id is not found "+employeeId );
        }
return theEmployee;
    }

    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody Employee theEmployee){
        theEmployee.setId(0);
        Employee dbEmployee=employeeService.save(theEmployee);
        return dbEmployee;
    }
    @PutMapping("/employee")
    public Employee updateEmployee(@RequestBody Employee theEmployee){
        Employee dbEmployee=employeeService.save(theEmployee);
        return dbEmployee;
    }
    @PatchMapping("/employee/{employeeId}")
    public Employee patchEmployee(@PathVariable int employeeId ,@RequestBody Map<String,Object>patchByLoad)  {
        Employee tempEmployee = employeeService.findById(employeeId);
        //throw exception if null
        if (tempEmployee == null) {
            throw new RuntimeException("Employee id is not found --" + employeeId);
        }
//throw exception if request body contains "id" key
        if (patchByLoad.containsKey("id")) {
            throw new RuntimeException("Employee id is not allowed in request body -- " + employeeId);
        }
        try {
            Employee updatedEmployee = objectMapper.updateValue(tempEmployee,patchByLoad);
            Employee dbEmployee = employeeService.save(updatedEmployee);
            return dbEmployee;
        }
        catch (Exception e){
            throw new RuntimeException("Patch Failed --"+e.getMessage());
        }
    }
    //delete an employee
    @DeleteMapping("/employee/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId){
         Employee tempEmployee=employeeService.findById(employeeId);
         if(tempEmployee==null) {
             throw new RuntimeException("This employee does not exist --"+employeeId);
         }
         employeeService.deleteById(employeeId);
         return "Delete employee --"+employeeId;
    }

}
