package az.the_best.onlinecourseplatform.security;

import az.the_best.onlinecourseplatform.entities.roles.Student;
import az.the_best.onlinecourseplatform.repo.course.RestCourseRepo;
import az.the_best.onlinecourseplatform.repo.course.RestEnrollmentRepo;
import az.the_best.onlinecourseplatform.repo.role.RestStudentRepo;
import az.the_best.onlinecourseplatform.repo.role.RestUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("courseSecurity")
@RequiredArgsConstructor
public class CourseSecurity {

    private final RestCourseRepo courseRepo;
    private final RestEnrollmentRepo enrollmentRepo;
    private final RestStudentRepo studentRepo;
    private final RestUserRepo userRepo;

    public boolean isOwnerOfCourse(Long courseId, Long userId) {
        return courseRepo.findById(courseId)
                .map(course -> userId.equals(course.getTeacher().getUser().getId()))
                .orElse(false);
    }


    public boolean isStudentOfCourse(Long courseId, Long userId) {
        if(userId == null) return false;

        if(!userRepo.existsById(userId)) return false;
        if(!courseRepo.existsById(courseId)) return false;

        Student student = studentRepo.findByUserId(userId).orElse(null);
        if(student == null) return false;

        return enrollmentRepo.existsByStudentIdAndCourseId(student.getId(), courseId);
    }
}
