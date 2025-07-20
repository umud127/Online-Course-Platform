package az.the_best.onlinecourseplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DTOUser {
    private String username;
    private String email;
    private String phoneNumber;
    private String role;
}
