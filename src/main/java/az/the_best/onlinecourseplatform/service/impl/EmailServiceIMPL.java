package az.the_best.onlinecourseplatform.service.impl;

import az.the_best.onlinecourseplatform.dto.DTOEmailRequest;
import az.the_best.onlinecourseplatform.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceIMPL implements IEmailService {

    private final JavaMailSender mailSender;
    private final String companyMail;

    @Autowired
    public EmailServiceIMPL(JavaMailSender mailSender, @Value("${EMAIL_USERNAME}") String companyMail) {
        this.mailSender = mailSender;
        this.companyMail = companyMail;
    }

    @Override
    public void sendOTPCodeByEmail(DTOEmailRequest emailRequest, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(companyMail);
        message.setTo(emailRequest.getEmail());
        message.setSubject("OTP Code");

        message.setText("Sizin OTP kodunuz: " + otp + "\n\nBu kod 1 dəqiqə ərzində keçərlidir.");

        mailSender.send(message);
    }

    @Override
    public void sendEmailToEveryone(String  email) {

    }
}
