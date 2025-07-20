package az.the_best.onlinecourseplatform.service.impl;

import az.the_best.onlinecourseplatform.exception.BaseException;
import az.the_best.onlinecourseplatform.exception.ErrorMessage;
import az.the_best.onlinecourseplatform.exception.MessageType;
import az.the_best.onlinecourseplatform.service.ICloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceIMPL implements ICloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryServiceIMPL(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage() + ".... Progw2n't upload");
            throw new BaseException(new ErrorMessage(MessageType.SERVER_ERROR,null));
        }
    }
}
