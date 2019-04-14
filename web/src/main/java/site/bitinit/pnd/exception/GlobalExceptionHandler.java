package site.bitinit.pnd.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.bitinit.pnd.common.ResponseEntity;
import site.bitinit.pnd.common.exception.IllegalDataException;
import site.bitinit.pnd.common.util.ResponseUtils;

/**
 * @author: john
 * @date: 2019/4/2
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity illegalDataException(IllegalDataException e){
        logger.warn("[user-error]", e);
        return ResponseUtils.badRequest(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity notFoundException(HttpRequestMethodNotSupportedException e){
        return ResponseUtils.notFound("404 not found");
    }

    @ExceptionHandler
    public ResponseEntity systemDealFailException(SystemDealFailException e){
        logger.warn("[system-warn]", e);
        return ResponseUtils.serverError(e.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity exception(Exception e){
        logger.error("[system-error] ", e);
        return ResponseUtils.serverError("服务器错误，请联系管理员");
    }
}
