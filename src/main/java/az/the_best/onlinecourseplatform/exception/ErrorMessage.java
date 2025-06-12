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

    public String prepereMessage(){
        StringBuilder message = new StringBuilder();
        message.append(type.name());

        if(ofStatic != null){
            message.append(" : ").append(ofStatic);
        }

        return message.toString();
    }
}
