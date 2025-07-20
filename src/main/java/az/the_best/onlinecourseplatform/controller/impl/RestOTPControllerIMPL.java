package az.the_best.onlinecourseplatform.controller.impl;

import az.the_best.onlinecourseplatform.controller.IRestOTPController;
import az.the_best.onlinecourseplatform.dto.DTOEmailRequest;
import az.the_best.onlinecourseplatform.dto.DTOOTPVerifyRequest;
import az.the_best.onlinecourseplatform.entities.BaseEntity;
import az.the_best.onlinecourseplatform.exception.MessageType;
import az.the_best.onlinecourseplatform.service.IEmailService;
import az.the_best.onlinecourseplatform.service.IRestOTPService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/otp")
public class RestOTPControllerIMPL implements IRestOTPController {

    private final IRestOTPService otpService;
    private final IEmailService emailService;

    public RestOTPControllerIMPL(IRestOTPService otpService, IEmailService emailService) {
        this.otpService = otpService;
        this.emailService = emailService;
    }

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
