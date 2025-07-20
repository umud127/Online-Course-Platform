package az.the_best.onlinecourseplatform.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    //USER
    NO_DATA_EXIST("1000","Data Was not found"),
    USER_NOT_FOUND("1001","User Was Not Found"),
    INVALID_PASSWORD("1002","Invalid Password"),

    //SECURITY
    INVALID_REQUEST("2000","Invalid Request"),
    INVALID_TOKEN("2001","Invalid Token"),
    INVALID_OTP("2002","Invalid OTP"),
    UNAUTHORIZED("2003","Unauthorized"),

    //OTHER
    SERVER_ERROR("4000","Server Error"),
    GENERAL_EXCEPTION("9999","UNKNOWN ERROR"),
;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public final String code;

    public final String message;
}
