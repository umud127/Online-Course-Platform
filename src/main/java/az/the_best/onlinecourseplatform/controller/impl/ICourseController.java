package az.the_best.onlinecourseplatform.controller.impl;

import az.the_best.onlinecourseplatform.dto.DTOCourse;
import az.the_best.onlinecourseplatform.dto.DTOCourseIU;
import az.the_best.onlinecourseplatform.validation.NotEmptyMultipart;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICourseController {

        DTOCourse addCourse(@ModelAttribute @Valid DTOCourseIU dtoCourseIU, @NotEmptyMultipart @RequestPart(value = "file") MultipartFile file);

    DTOCourse getCourseById(Long id);

    List<DTOCourse> getCoursesByName(String name);

    DTOCourse editCourse(@ModelAttribute @Valid DTOCourseIU dtoCourseIU, @NotEmptyMultipart @RequestPart(value = "file") MultipartFile file, @PathVariable Long id);

    void deleteCourse(Long id);

    List<DTOCourse> getAllCourses();
}
