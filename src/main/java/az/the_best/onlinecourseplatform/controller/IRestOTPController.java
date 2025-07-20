package az.the_best.onlinecourseplatform.controller;

import az.the_best.onlinecourseplatform.dto.DTOEmailRequest;
import az.the_best.onlinecourseplatform.dto.DTOOTPVerifyRequest;
import az.the_best.onlinecourseplatform.entities.BaseEntity;

public interface IRestOTPController {

    BaseEntity<String> sendOtp(DTOEmailRequest dtoEmailRequest);

    BaseEntity<String> verifyOtp(DTOOTPVerifyRequest emailRequest);
}
