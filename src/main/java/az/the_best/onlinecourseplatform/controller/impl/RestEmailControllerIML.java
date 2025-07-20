package az.the_best.onlinecourseplatform.controller.impl;

import az.the_best.onlinecourseplatform.controller.IRestEmailController;
import az.the_best.onlinecourseplatform.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/email")
public class RestEmailControllerIML implements IRestEmailController {

    private final IEmailService emailService;

    @Autowired
    public RestEmailControllerIML(IEmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping(path = "/sendEmailToEveryone")
    @Override
    public void sendEmailToEveryone(@RequestBody String email) {
        emailService.sendEmailToEveryone(email);
    }
}
