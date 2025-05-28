package az.the_best.onlinecourseplatform.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD , ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyMultipartValidator.class)
public @interface NotEmptyMultipart {
    String message() default "File is empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
