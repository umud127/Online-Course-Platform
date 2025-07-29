package az.the_best.onlinecourseplatform.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {

    String uploadImage(MultipartFile file);

    String uploadVideo(MultipartFile file);
}
