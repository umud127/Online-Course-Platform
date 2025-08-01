package az.the_best.onlinecourseplatform.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String username;
    private String password;
    private String email;
    private String phoneNumber;
}
