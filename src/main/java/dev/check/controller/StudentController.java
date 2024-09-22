package dev.check.controller;

import dev.check.dto.ParamForGet;
import dev.check.dto.StudentFullTable;
import dev.check.dto.StudentUpdate;
import dev.check.FindError500;
import dev.check.manager.ManagerUtils;
import dev.check.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@FindError500
@RequestMapping("/api/base")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PutMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentUpdate changeStudent(@RequestBody StudentUpdate changingStudent) {
        return studentService.updateStudent(changingStudent);
    }

    @DeleteMapping(value = "students/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteStudent(@PathVariable("id") Long id) {
        studentService.removeStudent(id);
        return id;
    }

    @GetMapping(value = "students", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<StudentFullTable> getStudentsPagSortFilter(@ModelAttribute ParamForGet request) {
        return studentService.getStudents(request.getFilter(), ManagerUtils.createPageable(request.getPage(), request.getSize(), request.getColumn(), request.getDirection()));
    }

}
