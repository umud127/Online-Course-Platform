package az.the_best.onlinecourseplatform.controller;

import az.the_best.onlinecourseplatform.controller.impl.ICourseController;
import az.the_best.onlinecourseplatform.dto.DTOCourse;
import az.the_best.onlinecourseplatform.dto.IU.DTOCourseIU;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.service.impl.ICourseService;
import az.the_best.onlinecourseplatform.validation.NotEmptyMultipart;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/rest/api/course")
@Validated
public class CourseController implements ICourseController {

    @Autowired
    ICourseService courseService;

    @PostMapping(path = "/add" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public BaseEntity<DTOCourse> addCourse(@ModelAttribute @Valid DTOCourseIU dtoCourseIU, @NotEmptyMultipart @RequestPart(value = "file") MultipartFile file,Long id) {
        return courseService.addCourse(dtoCourseIU, file,id);
    }

    @GetMapping(path = "/{id}")
    @Override
    public BaseEntity<DTOCourse> getCourseById(@PathVariable(name = "id") Long id) {
        return courseService.getCourseById(id);
    }

    @GetMapping(path = "/search")
    public BaseEntity<List<DTOCourse>> getCoursesByName(@RequestParam(name = "name") String name) {
        return courseService.getCoursesByName(name);
    }

    @GetMapping(path = "/top5")
    @Override
    public BaseEntity<List<DTOCourse>> getTop5Courses() {
        return courseService.getTop5Courses();
    }

    @PutMapping(path = "/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public BaseEntity<DTOCourse> editCourse(@ModelAttribute @Valid DTOCourseIU dtoCourseIU, @NotEmptyMultipart @RequestPart(value = "file") MultipartFile file, @PathVariable Long id) {
        return courseService.editCourse(dtoCourseIU, file, id);
    }

    @DeleteMapping(path = "/delete/{id}")
    @Override
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    @GetMapping(path = "/all")
    @Override
    public BaseEntity<List<DTOCourse>> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PutMapping(path = "/{id}/increaseClickCount")
    @Override
    public void increaseClickCount(@PathVariable Long id) {
        courseService.increaseClickCount(id);
        System.out.println("increased click count");
    }
}