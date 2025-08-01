package az.the_best.onlinecourseplatform.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    //USER
    NO_DATA_EXIST("1000","Data Was not found"),
    USER_NOT_FOUND("1001","User Was Not Found"),
    INVALID_PASSWORD("1002","Invalid Password"),
    ALREADY_EXIST("1003","Already Exist"),

    //TEACHER
    TEACHER_NOT_FOUND("2000","Teacher Was Not Found"),
    COURSE_NOT_FOUND("2001","Course Was Not Found"),

    //SECURITY
    INVALID_REQUEST("3000","Invalid Request"),
    INVALID_TOKEN("3001","Invalid Token"),
    INVALID_OTP("3002","Invalid OTP"),
    UNAUTHORIZED("3003","Unauthorized"),

    //OTHER
    SERVER_ERROR("4000","Server Error"),
    INTERNAL_SERVER_ERROR("4001","Internal Server Error"),
    GENERAL_EXCEPTION("9999","UNKNOWN ERROR"),
;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public final String code;

    public final String message;
}
