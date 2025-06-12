package az.the_best.onlinecourseplatform.exception;

import lombok.Getter;

@Getter
public enum MessageType {

    NO_DATA_EXIST("1001","Data is not found"),
    SERVER_ERROR("4004","Server Error"),
    GENERAL_EXCEPTION("9999","UNKNOWN ERROR");

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String code;

    public String message;
}
