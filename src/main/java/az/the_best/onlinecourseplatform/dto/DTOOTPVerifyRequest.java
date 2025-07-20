package az.the_best.onlinecourseplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOOTPVerifyRequest {

    private String email;
    private String otp;
}
