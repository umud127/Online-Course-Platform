package az.the_best.onlinecourseplatform.controller.impl;

import az.the_best.onlinecourseplatform.dto.DTOUser;
import az.the_best.onlinecourseplatform.dto.IU.DTOUserIU;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserController {
    DTOUser addUser(@ModelAttribute @Valid DTOUserIU dtoUserIU, @RequestPart(value = "file") MultipartFile file);

    DTOUser getUserById(Long id);

    DTOUser editUser(@ModelAttribute @Valid DTOUserIU dtoUserIU, @RequestPart(value = "file") MultipartFile file, @PathVariable Long id);

    void deleteUser(Long id);

    List<DTOUser> getAllCUsers();

}
