package az.the_best.onlinecourseplatform.service;

import az.the_best.onlinecourseplatform.dto.DTOCourse;
import az.the_best.onlinecourseplatform.dto.DTOCourseIU;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICourseService {

    DTOCourse addCourse(DTOCourseIU dtoCourseIU, MultipartFile file);

    DTOCourse getCourseById(Long id);

    DTOCourse editCourse(DTOCourseIU dtoCourseIU, Long id);

    void deleteCourse(Long id);

    List<DTOCourse> getAllCourses();
}
