package az.the_best.onlinecourseplatform.service.impl;

import az.the_best.onlinecourseplatform.dto.DTOCourse;
import az.the_best.onlinecourseplatform.dto.IU.DTOCourseIU;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICourseService {

    DTOCourse addCourse(DTOCourseIU dtoCourseIU, MultipartFile file,Long id);

    DTOCourse getCourseById(Long id);

    List<DTOCourse> getCoursesByName(String name);

    List<DTOCourse> getTop5Courses();

    DTOCourse editCourse(DTOCourseIU dtoCourseIU, MultipartFile file, Long id);

    void deleteCourse(Long id);

    List<DTOCourse> getAllCourses();
}
