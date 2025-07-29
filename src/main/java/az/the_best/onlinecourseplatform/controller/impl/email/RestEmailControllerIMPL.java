package az.the_best.onlinecourseplatform.controller.impl.email;

import az.the_best.onlinecourseplatform.controller.interfaces.email.IRestEmailController;
import az.the_best.onlinecourseplatform.service.interfaces.email.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/email")
@RequiredArgsConstructor
public class RestEmailControllerIMPL implements IRestEmailController {

    private final IEmailService emailService;

    @PostMapping(path = "/sendEmailToEveryone")
    @Override
    public void sendEmailToEveryone(@RequestBody String email) {
        emailService.sendEmailToEveryone(email);
    }
}
