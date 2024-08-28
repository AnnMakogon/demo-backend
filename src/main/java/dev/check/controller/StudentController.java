package dev.check.controller;

import dev.check.DTO.ParamForGet;
import dev.check.DTO.StudentFullTable;
import dev.check.DTO.StudentUpdate;
import dev.check.FindError500;
import dev.check.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@FindError500
@RequestMapping("/api/base")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PutMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentUpdate changeStudent(@RequestBody StudentUpdate changingStudent) {
        return studentService.updateStudent(changingStudent);
    }

    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<StudentFullTable> getStudentsPagSortFilter(@ModelAttribute ParamForGet request) {
        return studentService.getStudents(request.getFilter(), ControllerUtils.createPageable(request.getPage(), request.getSize(), request.getColumn(), request.getDirection()));
    }

}
