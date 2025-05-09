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
        return null;
    }

    @Override
    public DTOCourse editCourse(DTOCourseIU dtoCourseIU, Long id) {
        return null;
    }

    @Override
    public void deleteCourse(Long id) {

    }

    @Override
    public List<DTOCourse> getAllCourses() {
        return List.of();
    }
}
