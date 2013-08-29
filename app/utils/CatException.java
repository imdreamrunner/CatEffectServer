package utils;

public class CatException extends Exception {
    private Integer code;
    private String message;

    public CatException(Integer errorCode) {
        code = errorCode;
        message = "";
    }

    public CatException(Integer errorCode, String errorMessage) {
        code = errorCode;
        message = errorMessage;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
