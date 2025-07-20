package az.the_best.onlinecourseplatform.service;

import az.the_best.onlinecourseplatform.dto.DTOEmailRequest;

public interface IEmailService {

    void sendOTPCodeByEmail(DTOEmailRequest emailRequest, String otp);

    void sendEmailToEveryone(String email);
}
