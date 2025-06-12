package az.the_best.onlinecourseplatform.controller;

import az.the_best.onlinecourseplatform.controller.impl.IUserController;
import az.the_best.onlinecourseplatform.dto.DTOUser;
import az.the_best.onlinecourseplatform.dto.IU.DTOUserIU;
import az.the_best.onlinecourseplatform.service.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Validated
@RequestMapping(path = "rest/api/user")
public class UserController implements IUserController {

    @Autowired
    private IUserService userService;

    @Override
    public DTOUser addUser(DTOUserIU dtoUserIU, MultipartFile file) {
        return userService.addUser(dtoUserIU, file);
    }

    @Override
    public DTOUser getUserById(Long id) {
        return userService.getUserById(id);
    }

    @Override
    public DTOUser editUser(DTOUserIU dtoUserIU, MultipartFile file, Long id) {
        return userService.editUser(dtoUserIU, file, id);
    }

    @Override
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }

    @Override
    public List<DTOUser> getAllCUsers() {
        return userService.getAllCUsers();
    }
}
