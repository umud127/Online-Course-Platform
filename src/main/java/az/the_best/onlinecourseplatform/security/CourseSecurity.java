package az.the_best.onlinecourseplatform.security;

import az.the_best.onlinecourseplatform.repo.RestCourseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("courseSecurity")
@RequiredArgsConstructor
public class CourseSecurity {

    private final RestCourseRepo courseRepo;

    public boolean isOwnerOfCourse(Long courseId, Long userId) {
        return courseRepo.findById(courseId).isPresent()
                &&
                userId.equals(
                        courseRepo.findById(courseId).get().getTeacher().getUser().getId()
                );
    }
}
