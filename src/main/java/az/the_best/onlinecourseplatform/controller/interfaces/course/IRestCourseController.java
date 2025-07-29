package az.the_best.onlinecourseplatform.controller.interfaces.course;

import az.the_best.onlinecourseplatform.dto.response.DTOCourse;
import az.the_best.onlinecourseplatform.dto.iu.DTOCourseIU;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.validation.NotEmptyMultipart;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRestCourseController {

    BaseEntity<DTOCourse> addCourse(DTOCourseIU dtoCourseIU, MultipartFile coverPhoto, List<MultipartFile> videoFiles, String authHeader);


    BaseEntity<List<DTOCourse>> getCoursesByName(String name);

    BaseEntity<List<DTOCourse>> getTop5Courses();

    BaseEntity<DTOCourse> editCourse(
            @ModelAttribute DTOCourseIU dtoCourseIU,
            @NotEmptyMultipart @RequestPart(value = "file") MultipartFile file,
            @PathVariable Long id
    );


    void deleteCourse(Long id);

    BaseEntity<List<DTOCourse>> getAllCourses();

    void increaseClickCount(Long courseId);
}
