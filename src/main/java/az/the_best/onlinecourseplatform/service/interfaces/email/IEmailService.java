package az.the_best.onlinecourseplatform.service.interfaces.email;

import az.the_best.onlinecourseplatform.dto.request.DTOEmailRequest;

public interface IEmailService {

    void sendAdminResponse(DTOEmailRequest emailRequest, String message);

    void sendOTPCodeByEmail(DTOEmailRequest emailRequest, String otp);

    void sendEmailToEveryone(String email);
}
