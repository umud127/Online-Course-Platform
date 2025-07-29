package az.the_best.onlinecourseplatform.service.impl.role.response;

import az.the_best.onlinecourseplatform.dto.request.DTOEmailRequest;
import az.the_best.onlinecourseplatform.dto.response.DTOAdminResponse;
import az.the_best.onlinecourseplatform.dto.response.DTOTeacherRequest;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.entities.roles.Teacher;
import az.the_best.onlinecourseplatform.entities.roles.User;
import az.the_best.onlinecourseplatform.entities.roles.request.Teacher_Request;
import az.the_best.onlinecourseplatform.exception.MessageType;
import az.the_best.onlinecourseplatform.repo.role.RestTeacherRepo;
import az.the_best.onlinecourseplatform.repo.role.RestUserRepo;
import az.the_best.onlinecourseplatform.repo.role.request.RestTeacherRequestRepo;
import az.the_best.onlinecourseplatform.repo.role.response.RestAdminResponseRepo;
import az.the_best.onlinecourseplatform.security.Decision;
import az.the_best.onlinecourseplatform.security.RoleName;
import az.the_best.onlinecourseplatform.service.interfaces.email.IEmailService;
import az.the_best.onlinecourseplatform.service.interfaces.role.response.IRestAdminResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestAdminResponseServiceIMPL implements IRestAdminResponseService {

    private final RestAdminResponseRepo adminResponseRepo;

    private final RestTeacherRequestRepo teacherRequestRepo;

    private final RestTeacherRepo teacherRepo;

    private final RestUserRepo userRepo;

    private final IEmailService emailService;

    @Override
    public BaseEntity<?> giveResponse(DTOAdminResponse response) {
        if (response.getDecision() == Decision.ACCEPT) {
            Optional<User> dbUser = userRepo.findById(response.getUserId());
            User user;

            if (dbUser.isPresent()) {
                user = dbUser.get();
            } else {
                return BaseEntity.notOk(MessageType.USER_NOT_FOUND, response.getUserId().toString() );
            }

            user.setRole(RoleName.ROLE_TEACHER);
            user.setTeacherRequest(null);
            userRepo.save(user);

            Teacher teacher = new Teacher();
            teacher.setUser(user);
            teacherRepo.save(teacher);

            teacherRequestRepo.deleteById(response.getTeacherRequestId());

            return BaseEntity.ok("Success");
        } else {
            Optional<User> dbUser = userRepo.findById(response.getUserId());
            User user;

            if (dbUser.isPresent()) {
                user = dbUser.get();
            } else {
                return BaseEntity.notOk(MessageType.USER_NOT_FOUND, response.getUserId().toString() );
            }

            DTOEmailRequest emailRequest = new DTOEmailRequest();
            emailRequest.setEmail(user.getEmail());
            emailService.sendAdminResponse(emailRequest, response.getMessageToTeacher());

            user.setTeacherRequest(null);
            userRepo.save(user);
            
            teacherRequestRepo.deleteById(response.getTeacherRequestId());

            return BaseEntity.ok("Success");
        }
    }

    @Override
    public BaseEntity<?> getTeacherResponseByRequestId(Long teacherRequestId) {
        return null;
    }

    @Override
    public BaseEntity<?> getAllRequests(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Teacher_Request> pageRequests = teacherRequestRepo.findAll(pageable);

        List<DTOTeacherRequest> content = pageRequests.getContent().stream()
                .map(req -> new DTOTeacherRequest(
                        req.getId(),
                        req.getDescription(),
                        req.getMessageToAdmin(),
                        req.getSubjects(),
                        req.getPhotoUrl(),
                        req.getUser() != null ? req.getUser().getId() : null
                ))
                .toList();

        return BaseEntity.ok(Map.of(
                "content", content,
                "currentPage", pageRequests.getNumber(),
                "totalPages", pageRequests.getTotalPages(),
                "totalElements", pageRequests.getTotalElements()
        ));
    }
}
