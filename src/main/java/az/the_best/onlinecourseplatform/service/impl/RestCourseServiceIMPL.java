package az.the_best.onlinecourseplatform.service.impl;

import az.the_best.onlinecourseplatform.dto.DTOCourse;
import az.the_best.onlinecourseplatform.dto.IU.DTOCourseIU;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.entities.course.Course;
import az.the_best.onlinecourseplatform.entities.roles.Teacher;
import az.the_best.onlinecourseplatform.entities.roles.User;
import az.the_best.onlinecourseplatform.exception.BaseException;
import az.the_best.onlinecourseplatform.exception.ErrorMessage;
import az.the_best.onlinecourseplatform.exception.MessageType;
import az.the_best.onlinecourseplatform.repo.RestCourseRepo;
import az.the_best.onlinecourseplatform.repo.RestTeacherRepo;
import az.the_best.onlinecourseplatform.repo.RestUserRepo;
import az.the_best.onlinecourseplatform.service.IRestCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static az.the_best.onlinecourseplatform.entities.BaseEntity.notOk;
import static az.the_best.onlinecourseplatform.entities.BaseEntity.ok;

@Service
public class RestCourseServiceIMPL implements IRestCourseService {

    @Autowired
    RestCourseRepo restCourseRepo;

    @Autowired
    RestUserRepo userRepo;

    @Autowired
    private RestTeacherRepo teacherRepo;

    @Autowired
    CloudinaryServiceIMPL cloudinaryServiceIMPL;

    @Override
    public BaseEntity<DTOCourse> addCourse(DTOCourseIU dtoCourseIU, MultipartFile file, Long userId) {
        Course course = new Course();
        BeanUtils.copyProperties(dtoCourseIU, course);


        Optional<User> optionalDbUser = userRepo.findById(userId);
        User dbUser;
        if (optionalDbUser.isPresent()) {
            dbUser = optionalDbUser.get();
        }


        Optional<Teacher> optionalUser = teacherRepo.findByUserId(userId);
        Teacher teacher;
        if (optionalUser.isPresent()) {
            teacher = optionalUser.get();
        } else {
            throw new BaseException(new ErrorMessage(MessageType.NO_DATA_EXIST,userId.toString()));
        }//duzelis lazimdir random xeta atmisam

        course.setTeacher(teacher);

        if(file != null) {
            String imageUrl = cloudinaryServiceIMPL.uploadImage(file);
            course.setCoverPhoto(imageUrl);
        }

        Course savedCourse = restCourseRepo.save(course);

        DTOCourse dtoCourse = new DTOCourse();
        BeanUtils.copyProperties(savedCourse, dtoCourse);

        return ok(dtoCourse);
    }

    @Override
    public BaseEntity<DTOCourse> getCourseById(Long id) {
        Course course = restCourseRepo.findById(id).orElse(null);
        DTOCourse dtoCourse = new DTOCourse();

        if(course == null) {
//            throw new BaseException(new ErrorMessage(MessageType.NO_DATA_EXIST,id.toString()));
            return notOk(MessageType.NO_DATA_EXIST,id.toString());
        }else{
            BeanUtils.copyProperties(course, dtoCourse);
        }

        return ok(dtoCourse);
    }

    @Override
    public BaseEntity<List<DTOCourse>> getCoursesByName(String name) {
        List<Course> courses = restCourseRepo.findByNameStartingWithIgnoreCase(name);

        if(courses.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_DATA_EXIST,name));
        }

        List<DTOCourse> filteredCourses = new ArrayList<>();

        for (Course course : courses) {
            DTOCourse dtoCourse = new DTOCourse();
            BeanUtils.copyProperties(course, dtoCourse);
            filteredCourses.add(dtoCourse);
        }

        return ok(filteredCourses);
    }

    @Override
    public BaseEntity<List<DTOCourse>> getTop5Courses() {
        List<Course> courses = restCourseRepo.findTop5ByOrderByClickCountDesc();
        List<DTOCourse> dtos = new ArrayList<>();

        for (Course course : courses) {
            DTOCourse dto = new DTOCourse();
            BeanUtils.copyProperties(course, dto);
            dtos.add(dto);
        }

        return ok(dtos);
    }

    @Override
    public BaseEntity<DTOCourse> editCourse(DTOCourseIU dtoCourseIU, MultipartFile file, Long id) {
        Course course = restCourseRepo.findById(id).orElse(null);

        if(course != null) {
            BeanUtils.copyProperties(dtoCourseIU, course);
            restCourseRepo.save(course);

            DTOCourse dtoCourse = new DTOCourse();
            BeanUtils.copyProperties(course, dtoCourse);
            return ok(dtoCourse);
        }

        return notOk(MessageType.NO_DATA_EXIST,id.toString());
    }

    @Override
    public void deleteCourse(Long id) {
        restCourseRepo.deleteById(id);
    }

    @Override
    public BaseEntity<List<DTOCourse>> getAllCourses() {
        List<Course> courses = restCourseRepo.findAll();

        List<DTOCourse> coursesDTO = new ArrayList<>(courses.size());

        for (Course course : courses) {
            DTOCourse dtoCourse = new DTOCourse();
            BeanUtils.copyProperties(course, dtoCourse);
            coursesDTO.add(dtoCourse);
        }
        return ok(coursesDTO);
    }

    @Override
    public void increaseClickCount(Long id) {
        restCourseRepo.increaseClickCount(id);
    }
}
