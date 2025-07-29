package az.the_best.onlinecourseplatform.controller.interfaces.email;

import az.the_best.onlinecourseplatform.dto.request.DTOEmailRequest;
import az.the_best.onlinecourseplatform.dto.request.DTOOTPVerifyRequest;
import az.the_best.onlinecourseplatform.entities.BaseEntity;

public interface IRestOTPController {

    BaseEntity<String> sendOtp(DTOEmailRequest dtoEmailRequest);

    BaseEntity<String> verifyOtp(DTOOTPVerifyRequest emailRequest);
}
