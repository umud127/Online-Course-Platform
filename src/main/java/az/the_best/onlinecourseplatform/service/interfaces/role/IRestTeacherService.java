package az.the_best.onlinecourseplatform.service.interfaces.role;

import az.the_best.onlinecourseplatform.entities.BaseEntity;

public interface IRestTeacherService {

    BaseEntity<?> getMyCourses(String token);
}
