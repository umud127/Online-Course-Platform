package az.the_best.onlinecourseplatform.service;

import az.the_best.onlinecourseplatform.dto.DTOUser;
import az.the_best.onlinecourseplatform.dto.IU.DTOUserIU;
import az.the_best.onlinecourseplatform.entities.User;
import az.the_best.onlinecourseplatform.repo.UserRepo;
import az.the_best.onlinecourseplatform.service.impl.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public DTOUser addUser(DTOUserIU dtoUserIU, MultipartFile file) {
        User newUser = new User();
        DTOUser dtoUser = new DTOUser();

        BeanUtils.copyProperties(dtoUserIU, newUser);

        if(!file.isEmpty()) {

        }
        return null;
    }

    @Override
    public DTOUser getUserById(Long id) {
        return null;
    }

    @Override
    public DTOUser editUser(DTOUserIU dtoUserIU, MultipartFile file, Long id) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public List<DTOUser> getAllCUsers() {
        return List.of();
    }
}
