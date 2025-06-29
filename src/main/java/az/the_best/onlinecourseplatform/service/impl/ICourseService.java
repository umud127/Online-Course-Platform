package az.the_best.onlinecourseplatform.service.impl;

import az.the_best.onlinecourseplatform.dto.DTOCourse;
import az.the_best.onlinecourseplatform.dto.IU.DTOCourseIU;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.validation.NotEmptyMultipart;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICourseService {

    BaseEntity<DTOCourse> addCourse(@ModelAttribute @Valid DTOCourseIU dtoCourseIU, @NotEmptyMultipart @RequestPart(value = "file") MultipartFile file, Long id);

    BaseEntity<DTOCourse> getCourseById(Long id);

    BaseEntity<List<DTOCourse>> getCoursesByName(String name);

    BaseEntity<List<DTOCourse>> getTop5Courses();

    BaseEntity<DTOCourse> editCourse(@ModelAttribute @Valid DTOCourseIU dtoCourseIU, @NotEmptyMultipart @RequestPart(value = "file") MultipartFile file, @PathVariable Long id);

    void deleteCourse(Long id);

    BaseEntity<List<DTOCourse>> getAllCourses();

    void increaseClickCount(@PathVariable Long id);
}
