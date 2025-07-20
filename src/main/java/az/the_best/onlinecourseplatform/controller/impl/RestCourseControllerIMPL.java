package az.the_best.onlinecourseplatform.controller.impl;

import az.the_best.onlinecourseplatform.controller.IRestCourseController;
import az.the_best.onlinecourseplatform.dto.DTOCourse;
import az.the_best.onlinecourseplatform.dto.IU.DTOCourseIU;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.service.IRestCourseService;
import az.the_best.onlinecourseplatform.validation.NotEmptyMultipart;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/rest/api/course")
@Validated
public class RestCourseControllerIMPL implements IRestCourseController {

    private final IRestCourseService courseService;

    public RestCourseControllerIMPL(IRestCourseService courseService) {
        this.courseService = courseService;
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(path = "/teacher/add" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public BaseEntity<DTOCourse> addCourse(
            @ModelAttribute @Valid DTOCourseIU dtoCourseIU,
            @NotEmptyMultipart @RequestPart(value = "file") MultipartFile file,
            Long id) {
        return courseService.addCourse(dtoCourseIU, file,id);
    }

    @GetMapping(path = "/public/search")
    public BaseEntity<List<DTOCourse>> getCoursesByName(@RequestParam(name = "name") String name) {
        return courseService.getCoursesByName(name);
    }

    @GetMapping(path = "/public/top5")
    @Override
    public BaseEntity<List<DTOCourse>> getTop5Courses() {
        return courseService.getTop5Courses();
    }

    @PreAuthorize("hasRole('ROLE_TEACHER') and courseSecurity.isOwnerOfCourse(#id,authentication.principal.id)")
    @PutMapping(path = "/teacher/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public BaseEntity<DTOCourse> editCourse(
            @ModelAttribute @Valid DTOCourseIU dtoCourseIU,
            @NotEmptyMultipart @RequestPart(value = "file") MultipartFile file,
            @PathVariable Long id) {
        return courseService.editCourse(dtoCourseIU, file, id);
    }

    @PreAuthorize("hasRole('ROLE_TEACHER') and courseSecurity.isOwnerOfCourse(#id,authentication.principal.id)")
    @DeleteMapping(path = "/teacher/delete/{id}")
    @Override
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    @GetMapping(path = "/all")
    @Override
    public BaseEntity<List<DTOCourse>> getAllCourses() {
        return courseService.getAllCourses();
    }

    //limit to this method and protect someone who wants to be famous through this way
    @PutMapping(path = "/public/increaseClickCount/{id}")
    @Override
    public void increaseClickCount(@PathVariable Long id) {
        courseService.increaseClickCount(id);
    }
}