package az.the_best.onlinecourseplatform.service.interfaces.email;

import az.the_best.onlinecourseplatform.dto.request.DTOEmailRequest;

public interface IRestOTPService {

    String generateOTP(DTOEmailRequest dtoEmailRequest);

    boolean verifyOTP(String email, String otp);
}
