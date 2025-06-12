package az.the_best.onlinecourseplatform.service;

import az.the_best.onlinecourseplatform.dto.DTOCourse;
import az.the_best.onlinecourseplatform.dto.IU.DTOCourseIU;
import az.the_best.onlinecourseplatform.entities.Course;
import az.the_best.onlinecourseplatform.entities.User;
import az.the_best.onlinecourseplatform.exception.BaseException;
import az.the_best.onlinecourseplatform.exception.ErrorMessage;
import az.the_best.onlinecourseplatform.exception.MessageType;
import az.the_best.onlinecourseplatform.repo.CourseRepo;
import az.the_best.onlinecourseplatform.repo.UserRepo;
import az.the_best.onlinecourseplatform.service.impl.ICourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements ICourseService {

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CloudinaryService cloudinaryService;

    @Override
    public DTOCourse addCourse(DTOCourseIU dtoCourseIU, MultipartFile file,Long userId) {
        Course course = new Course();

        BeanUtils.copyProperties(dtoCourseIU, course);

        Optional<User> optionalUser = userRepo.findById(userId);

        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            course.setUser(user);
        } else {
            throw new BaseException(new ErrorMessage(MessageType.NO_DATA_EXIST,userId.toString()));
        }

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

        if(course == null) {
            throw new BaseException(new ErrorMessage(MessageType.NO_DATA_EXIST,id.toString()));
        }else{
            DTOCourse dtoCourse = new DTOCourse();
            BeanUtils.copyProperties(course, dtoCourse);
            return dtoCourse;
        }
    }

    @Override
    public List<DTOCourse> getCoursesByName(String name) {
        List<Course> courses = courseRepo.findByNameStartingWithIgnoreCase(name);

        if(courses.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_DATA_EXIST,name));
        }

        List<DTOCourse> filteredCourses = new ArrayList<>();

        for (Course course : courses) {
            DTOCourse dtoCourse = new DTOCourse();
            BeanUtils.copyProperties(course, dtoCourse);
            filteredCourses.add(dtoCourse);
        }

        return filteredCourses;
    }

    @Override
    public List<DTOCourse> getTop5Courses() {
        List<Course> courses = courseRepo.findTop5ByOrderByClickCountDesc();
        List<DTOCourse> dtos = new ArrayList<>();

        for (Course course : courses) {
            DTOCourse dto = new DTOCourse();
            BeanUtils.copyProperties(course, dto);
            dtos.add(dto);
        }

        return dtos;
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
