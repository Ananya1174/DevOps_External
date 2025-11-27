package com.crud.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crud.model.Student;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private Map<Long, Student> studentStore = new HashMap<>();
    private AtomicLong idGenerator = new AtomicLong(1);

    // CREATE
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Long id = idGenerator.getAndIncrement();
        student.setId(id);
        studentStore.put(id, student);
        return ResponseEntity.ok(student);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        return ResponseEntity.ok(studentStore.values());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentStore.get(id);
        return student == null ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(student);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student updatedStudent) {

        Student existing = studentStore.get(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setName(updatedStudent.getName());
        existing.setEmail(updatedStudent.getEmail());
        return ResponseEntity.ok(existing);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        Student removed = studentStore.remove(id);
        return removed == null ? ResponseEntity.notFound().build()
                : ResponseEntity.noContent().build();
    }
}