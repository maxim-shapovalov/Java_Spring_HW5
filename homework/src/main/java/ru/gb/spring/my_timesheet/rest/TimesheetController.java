package ru.gb.spring.my_timesheet.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring.my_timesheet.model.Timesheet;
import ru.gb.spring.my_timesheet.service.TimesheetService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/timesheets")
public class TimesheetController {
    private final TimesheetService service;

    public TimesheetController(TimesheetService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Timesheet> get(@PathVariable Long id) {
        Optional<Timesheet> ts = service.findById(id);
        if(ts.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(ts.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping()
    public ResponseEntity<List<Timesheet>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/after")
    public ResponseEntity<List<Timesheet>> getCreatedAfter(@RequestParam LocalDate createdAtAfter){
        return ResponseEntity.status(HttpStatus.OK).body(service.findByCreatedAfter(createdAtAfter));
    }

    @GetMapping("/before")
    public ResponseEntity<List<Timesheet>> getCreatedBefore(@RequestParam LocalDate createdAtBefore){
        return ResponseEntity.status(HttpStatus.OK).body(service.findByCreatedBefore(createdAtBefore));
    }

    @PostMapping // создание нового ресурса
    public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet) {
        if(service.getProjectById(timesheet.getProjectId()).isPresent()){
            timesheet = service.create(timesheet);
            // 201 Created
            return ResponseEntity.status(HttpStatus.CREATED).body(timesheet);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(timesheet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        // 204 No Content
        return ResponseEntity.noContent().build();
    }
}
