package az.the_best.onlinecourseplatform.controller.impl.email;

import az.the_best.onlinecourseplatform.controller.interfaces.email.IRestOTPController;
import az.the_best.onlinecourseplatform.dto.request.DTOEmailRequest;
import az.the_best.onlinecourseplatform.dto.request.DTOOTPVerifyRequest;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.exception.MessageType;
import az.the_best.onlinecourseplatform.service.interfaces.email.IEmailService;
import az.the_best.onlinecourseplatform.service.interfaces.email.IRestOTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/otp")
@RequiredArgsConstructor
public class RestOTPControllerIMPL implements IRestOTPController {

    private final IRestOTPService otpService;
    private final IEmailService emailService;


    @PostMapping("/send")
    public BaseEntity<String> sendOtp(@RequestBody DTOEmailRequest emailRequest) {
        String otp = otpService.generateOTP(emailRequest);
        emailService.sendOTPCodeByEmail(emailRequest,otp);

        String successMSG = "OTP kodu emailə göndərildi!";

        return BaseEntity.ok(successMSG);
    }

    @PostMapping("/verify")
    public BaseEntity<String> verifyOtp(@RequestBody DTOOTPVerifyRequest emailRequest) {
        boolean isValid = otpService.verifyOTP(emailRequest.getEmail(), emailRequest.getOtp());

        if (isValid) {
            return BaseEntity.ok("OTP kodu düzgündür!");
        } else {
            return BaseEntity.notOk(MessageType.INVALID_OTP, "Yanlış və ya vaxtı keçmiş OTP!");
        }
    }
}
