package az.the_best.onlinecourseplatform.service.impl.role.request;

import az.the_best.onlinecourseplatform.dto.iu.DTOTeacherRequestIU;
import az.the_best.onlinecourseplatform.entities.roles.User;
import az.the_best.onlinecourseplatform.entities.roles.request.Teacher_Request;
import az.the_best.onlinecourseplatform.jwt.JWTService;
import az.the_best.onlinecourseplatform.repo.role.request.RestTeacherRequestRepo;
import az.the_best.onlinecourseplatform.repo.role.RestUserRepo;
import az.the_best.onlinecourseplatform.service.impl.CloudinaryServiceIMPL;
import az.the_best.onlinecourseplatform.service.interfaces.role.request.IRestTeacherRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestTeacherRequestServiceIMPL implements IRestTeacherRequestService {

    private final CloudinaryServiceIMPL cloudinaryServiceIMPL;
    private final RestUserRepo userRepo;
    private final RestTeacherRequestRepo teacherRequestRepo;
    private final JWTService jWTService;

    @Override
    public void createRequest(DTOTeacherRequestIU request, String token) {
        Long userId = jWTService.extractId(token);

        User user = userRepo.findById(userId).orElseThrow( () -> new RuntimeException("User not found"));

        String url = cloudinaryServiceIMPL.uploadImage(request.getFile());

        Teacher_Request teacherRequest = new Teacher_Request();
        BeanUtils.copyProperties(request,teacherRequest);
        teacherRequest.setUser(user);
        teacherRequest.setPhotoUrl(url);
        teacherRequestRepo.save(teacherRequest);

        user.setTeacherRequest(teacherRequest);
        userRepo.save(user);
    }

    @Override
    public boolean checkTeacherHaveRequest(Long userId) {
        return teacherRequestRepo.existsByUserId_Id(userId);
    }
}
