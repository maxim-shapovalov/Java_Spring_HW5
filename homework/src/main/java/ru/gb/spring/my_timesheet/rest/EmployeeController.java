package ru.gb.spring.my_timesheet.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring.my_timesheet.model.Employee;
import ru.gb.spring.my_timesheet.model.Project;
import ru.gb.spring.my_timesheet.model.Timesheet;
import ru.gb.spring.my_timesheet.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService service;


    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable Long id){
        Optional<Employee> employee = service.findById(id);
        if(employee.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(employee.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee){
        employee = service.create(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getTimesheetsByEmployeeId(@PathVariable Long id){
        if(service.findById(id).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(service.getTimesheetsByEmployeeId(id));
        }
        return ResponseEntity.notFound().build();
    }
}
