package jungjin.common.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends BaseException {
    public BusinessException(String message) {
        super(HttpStatus.BAD_REQUEST, "BUSINESS_ERROR", message);
    }

    public BusinessException(String code, String message) {
        super(HttpStatus.BAD_REQUEST, code, message);
    }
}
