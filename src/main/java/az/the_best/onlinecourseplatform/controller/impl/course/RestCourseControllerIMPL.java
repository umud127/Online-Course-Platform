package az.the_best.onlinecourseplatform.controller.impl.course;

import az.the_best.onlinecourseplatform.controller.interfaces.course.IRestCourseController;
import az.the_best.onlinecourseplatform.dto.response.DTOCourse;
import az.the_best.onlinecourseplatform.dto.iu.DTOCourseIU;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.jwt.JWTService;
import az.the_best.onlinecourseplatform.security.CourseSecurity;
import az.the_best.onlinecourseplatform.service.interfaces.IRestCourseService;
import az.the_best.onlinecourseplatform.validation.NotEmptyMultipart;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/course")
@RequiredArgsConstructor
public class RestCourseControllerIMPL implements IRestCourseController {

    private final IRestCourseService courseService;
    private final JWTService jwtService;
    private final CourseSecurity courseSecurity;


    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @PostMapping(path = "/teacher/add" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public BaseEntity<DTOCourse> addCourse(
            @RequestPart("data") @Valid DTOCourseIU dtoCourseIU,
            @RequestPart("coverPhoto") MultipartFile coverPhoto,
            @RequestPart("videoFiles") List<MultipartFile> videoFiles,
            @RequestHeader("Authorization") String authHeader) {
        return courseService.addCourse(dtoCourseIU, coverPhoto, videoFiles, authHeader.substring(7));
    }

    @GetMapping(path = "/{id}")
    @Override
    public BaseEntity<DTOCourse> getCourseById(@PathVariable(value = "id") Long id) {
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

    @PreAuthorize("hasRole('ROLE_TEACHER') and courseSecurity.isOwnerOfCourse(#id,authentication.principal.id)")
    @PutMapping(path = "/edit/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    @PutMapping(path = "/increaseClickCount/{id}")
    @Override
    public void increaseClickCount(@PathVariable(value = "id") Long courseId) {
        courseService.increaseClickCount(courseId);
    }

    @PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_USER') or hasRole('ROLE_STUDENT')")
    @PostMapping(path = "/toEnroll/{id}")
    @Override
    public BaseEntity<String> getToEnroll(@PathVariable(value = "id") Long courseId, @RequestHeader("Authorization") String authHeader) {
        return courseService.getToEnroll(courseId, authHeader.substring(7));
    }

    @GetMapping(path = "/checkStudentHaveCourse/{id}")
    @Override
    public boolean checkStudentHaveCourse(@PathVariable(value = "id") Long courseId, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        } else {
            return courseSecurity.isStudentOfCourse(courseId, jwtService.extractId(authHeader.substring(7)));
        }
    }

    @PreAuthorize("hasRole('ROLE_TEACHER')")
    @GetMapping(path = "/checkTeacherHaveCourse/{id}")
    @Override
    public boolean checkTeacherHaveCourse(@PathVariable(value = "id") Long courseId, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        } else {
            return courseSecurity.isOwnerOfCourse(jwtService.extractId(authHeader.substring(7)), courseId);
        }
    }
}