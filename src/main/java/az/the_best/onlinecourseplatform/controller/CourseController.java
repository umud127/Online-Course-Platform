package az.the_best.onlinecourseplatform.controller;

import az.the_best.onlinecourseplatform.dto.DTOCourse;
import az.the_best.onlinecourseplatform.dto.DTOCourseIU;
import az.the_best.onlinecourseplatform.service.ICourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/rest/api/course")
public class CourseController implements ICourseController{

    @Autowired
    ICourseService courseService;

    @PostMapping(path = "/add" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public DTOCourse addCourse(@ModelAttribute @Valid DTOCourseIU dtoCourseIU, @RequestPart("file") MultipartFile file) {
        return courseService.addCourse(dtoCourseIU, file);
    }

    @GetMapping(path = "/{id}")
    @Override
    public DTOCourse getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PutMapping(path = "/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public DTOCourse editCourse(@ModelAttribute @Valid DTOCourseIU dtoCourseIU, @RequestPart("file") MultipartFile file, @PathVariable Long id) {
        return courseService.editCourse(dtoCourseIU, file, id);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    @GetMapping(path = "/all")
    @Override
    public List<DTOCourse> getAllCourses() {
        return courseService.getAllCourses();
    }
}