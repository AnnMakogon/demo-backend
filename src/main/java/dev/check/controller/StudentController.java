package dev.check.controller;

import com.sun.istack.NotNull;
import dev.check.FindError500;
import dev.check.dto.ParamForGet;
import dev.check.dto.Student;
import dev.check.dto.StudentUpdate;
import dev.check.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@FindError500
@RequestMapping("/api/base")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PutMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentUpdate changeStudent(@Validated @RequestBody StudentUpdate changingStudent) {
        return studentService.updateStudent(changingStudent);
    }

    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteStudent(@PathVariable("id") @NotNull Long id) {
        studentService.removeStudent(id);
        return id;
    }

    @GetMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Student> getStudentsPagSortFilter(@ModelAttribute ParamForGet request) {
        return studentService.getStudents(request);
    }

}
