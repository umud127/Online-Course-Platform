package az.the_best.onlinecourseplatform.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private MessageType type;

    private String ofStatic;

    public String prepareMessage(){
        if (ofStatic == null) {
            return type.name();
        }

        return type.name() + " : " + ofStatic;
    }
}
