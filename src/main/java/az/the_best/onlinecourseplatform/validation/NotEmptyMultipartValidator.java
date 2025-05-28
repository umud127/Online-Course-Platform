package az.the_best.onlinecourseplatform.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class NotEmptyMultipartValidator implements ConstraintValidator<NotEmptyMultipart, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return multipartFile != null && !multipartFile.isEmpty();
    }

}
