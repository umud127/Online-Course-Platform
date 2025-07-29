package az.the_best.onlinecourseplatform.service.impl.role;

import az.the_best.onlinecourseplatform.dto.response.DTOCourse;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.entities.course.Course;
import az.the_best.onlinecourseplatform.entities.roles.Teacher;
import az.the_best.onlinecourseplatform.entities.roles.User;
import az.the_best.onlinecourseplatform.exception.MessageType;
import az.the_best.onlinecourseplatform.jwt.JWTService;
import az.the_best.onlinecourseplatform.repo.course.RestCourseRepo;
import az.the_best.onlinecourseplatform.repo.role.RestTeacherRepo;
import az.the_best.onlinecourseplatform.repo.role.RestUserRepo;
import az.the_best.onlinecourseplatform.service.interfaces.role.IRestTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestTeacherServiceIMPL implements IRestTeacherService {

    private final RestTeacherRepo teacherRepo;

    private final JWTService jwtService;

    private final RestUserRepo userRepo;

    private final RestCourseRepo courseRepo;


    @Override
    public BaseEntity<?> getMyCourses(String token) {
        if (token == null || token.isBlank()) {
            return BaseEntity.notOk(MessageType.INVALID_TOKEN, "null");
        }

        Long userId = jwtService.extractId(token);

        Optional<User> dbUser = userRepo.findById(userId);
        User user;
        if (dbUser.isPresent()) {
            user = dbUser.get();
        } else {
            return BaseEntity.notOk(MessageType.USER_NOT_FOUND, userId.toString());
        }

        Optional<Teacher> dbTeacher = teacherRepo.findByUserId(userId);
        Teacher teacher;

        if (dbTeacher.isPresent()) {
            teacher = dbTeacher.get();
        } else {
            return BaseEntity.notOk(MessageType.TEACHER_NOT_FOUND, null);
        }

        Optional<List<Course>> courses = courseRepo.findByTeacherId(teacher.getId());
        List<Course> dbCourses;

        if (courses.isPresent()) {
            dbCourses = courses.get();
        } else {
            return BaseEntity.notOk(MessageType.COURSE_NOT_FOUND, null);
        }

        List<DTOCourse> dtoCourses = new ArrayList<>();

        for (Course course : dbCourses) {
            DTOCourse dtoCourse = new DTOCourse();
            BeanUtils.copyProperties(course, dtoCourse);
            dtoCourses.add(dtoCourse);
        }

        return BaseEntity.ok(dtoCourses);
    }
}
