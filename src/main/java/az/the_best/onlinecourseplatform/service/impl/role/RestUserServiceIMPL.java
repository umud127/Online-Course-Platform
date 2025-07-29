package az.the_best.onlinecourseplatform.service.impl.role;

import az.the_best.onlinecourseplatform.dto.response.DTOCourse;
import az.the_best.onlinecourseplatform.dto.response.DTOUser;
import az.the_best.onlinecourseplatform.dto.iu.DTOUserIU;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.entities.roles.User;
import az.the_best.onlinecourseplatform.jwt.JWTService;
import az.the_best.onlinecourseplatform.repo.role.RestUserRepo;
import az.the_best.onlinecourseplatform.security.RoleName;
import az.the_best.onlinecourseplatform.service.interfaces.role.IRestUserService;
import com.cloudinary.provisioning.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestUserServiceIMPL implements IRestUserService {

    private final RestUserRepo userRepo;

    private final JWTService jWTService;

    @Override
    public BaseEntity<?> getUserById(Long id) {
        User user = userRepo.findById(id)
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
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        if (dtoUserIU.getPassword() != null && !dtoUserIU.getPassword().isBlank()) {
            user.setPassword(new BCryptPasswordEncoder().encode(dtoUserIU.getPassword()));
        }
        userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public BaseEntity<?> getCoursesThatUserEnrolled(Long id) {
        List<DTOCourse> dbCourses = new ArrayList<>();

        return BaseEntity.ok(dbCourses);
    }

    @Override
    public BaseEntity<?> checkTeacherStatus(String authHeader) {

        Long id = jWTService.extractId(authHeader.substring(7));
        User user = userRepo.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id: " + id)
        );

        if (user.getRole() == RoleName.ROLE_TEACHER) {
            return BaseEntity.ok("Teacher");
        } else {
            if (user.getTeacherRequest() != null) {
                return BaseEntity.ok("Requested");
            }

            return BaseEntity.ok("User");
        }
    }
}
