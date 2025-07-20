package az.the_best.onlinecourseplatform.service;

import az.the_best.onlinecourseplatform.dto.DTOEmailRequest;

public interface IRestOTPService {

    String generateOTP(DTOEmailRequest dtoEmailRequest);

    boolean verifyOTP(String email, String otp);
}
