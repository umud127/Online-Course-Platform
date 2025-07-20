package az.the_best.onlinecourseplatform.service.impl;

import az.the_best.onlinecourseplatform.dto.DTOCourse;
import az.the_best.onlinecourseplatform.dto.DTOUser;
import az.the_best.onlinecourseplatform.dto.IU.DTOUserIU;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.entities.course.Course;
import az.the_best.onlinecourseplatform.entities.roles.User;
import az.the_best.onlinecourseplatform.repo.RestUserRepo;
import az.the_best.onlinecourseplatform.service.IRestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestUserServiceIMPL implements IRestUserService {

    @Autowired
    private RestUserRepo restUserRepo;

    @Override
    public BaseEntity<DTOUser> getUserById(Long id) {
        User user = restUserRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        DTOUser dtoUser =
                DTOUser.builder()
                .username(user.getRealUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().name())
                .build();

        return BaseEntity.ok(dtoUser);
    }

    @Override
    public void editUser(DTOUserIU dtoUserIU, Long id) {
        User user = restUserRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        if (dtoUserIU.getPassword() != null && !dtoUserIU.getPassword().isBlank()) {
            user.setPassword(new BCryptPasswordEncoder().encode(dtoUserIU.getPassword()));
        }
        restUserRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        restUserRepo.deleteById(id);
    }

    @Override
    public BaseEntity<List<DTOCourse>> getCoursesThatUserEnrolled(Long id) {
        List<DTOCourse> dbCourses = new ArrayList<>();

        return BaseEntity.ok(dbCourses);
    }
}
