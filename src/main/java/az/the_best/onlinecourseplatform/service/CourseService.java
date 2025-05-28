package az.the_best.onlinecourseplatform.service;

import az.the_best.onlinecourseplatform.dto.DTOCourse;
import az.the_best.onlinecourseplatform.dto.DTOCourseIU;
import az.the_best.onlinecourseplatform.entities.Course;
import az.the_best.onlinecourseplatform.repo.CourseRepo;
import az.the_best.onlinecourseplatform.service.impl.ICourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService implements ICourseService {

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    CloudinaryService cloudinaryService;

    @Override
    public DTOCourse addCourse(DTOCourseIU dtoCourseIU, MultipartFile file) {
        Course course = new Course();

        BeanUtils.copyProperties(dtoCourseIU, course);

        if(file != null) {
            String imageUrl = cloudinaryService.uploadImage(file);
            course.setCoverPhoto(imageUrl);
        }

        Course savedCourse = courseRepo.save(course);

        DTOCourse dtoCourse = new DTOCourse();
        BeanUtils.copyProperties(savedCourse, dtoCourse);

        return dtoCourse;
    }

    @Override
    public DTOCourse getCourseById(Long id) {
        Course course = courseRepo.findById(id).orElse(null);

        if(course != null) {
            DTOCourse dtoCourse = new DTOCourse();
            BeanUtils.copyProperties(course, dtoCourse);
            return dtoCourse;
        }

        return null;
    }

    @Override
    public List<DTOCourse> getCoursesByName(String name) {
        List<Course> courses = courseRepo.findByNameStartingWithIgnoreCase(name);

        List<DTOCourse> filteredCourses = new ArrayList<>();

        for (Course course : courses) {
            DTOCourse dtoCourse = new DTOCourse();
            BeanUtils.copyProperties(course, dtoCourse);
            filteredCourses.add(dtoCourse);
        }

        return filteredCourses;
    }

    @Override
    public DTOCourse editCourse(DTOCourseIU dtoCourseIU, MultipartFile file, Long id) {
        Course course = courseRepo.findById(id).orElse(null);

        if(course != null) {
            BeanUtils.copyProperties(dtoCourseIU, course);
            courseRepo.save(course);

            DTOCourse dtoCourse = new DTOCourse();
            BeanUtils.copyProperties(course, dtoCourse);
            return dtoCourse;
        }

        return null;
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepo.deleteById(id);
    }

    @Override
    public List<DTOCourse> getAllCourses() {
        List<Course> courses = courseRepo.findAll();

        List<DTOCourse> coursesDTO = new ArrayList<>(courses.size());

        for (Course course : courses) {
            DTOCourse dtoCourse = new DTOCourse();
            BeanUtils.copyProperties(course, dtoCourse);
            coursesDTO.add(dtoCourse);
        }
        return coursesDTO;
    }
}
