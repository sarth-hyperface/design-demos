package org.example

import org.example.service.WorkflowService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class TemporalDemo {
    @Autowired
    WorkflowService workflowService

    static void main(String[] args) {
        SpringApplication.run(TemporalDemo.class, args)
    }

    @GetMapping(value = "/test")
    ResponseEntity<Object> test() {
        workflowService.triggerParent()
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}
