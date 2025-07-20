package az.the_best.onlinecourseplatform.service;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {

    String uploadImage(MultipartFile file);
}
