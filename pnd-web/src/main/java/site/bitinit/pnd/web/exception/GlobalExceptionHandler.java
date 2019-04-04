package site.bitinit.pnd.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.bitinit.pnd.common.ResponseEntity;
import site.bitinit.pnd.common.util.ResponseUtils;

/**
 * @author: john
 * @date: 2019/4/2
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity exception(Exception e){
        logger.error("[system-error] ", e);
        return ResponseUtils.serverError("服务器错误，请联系管理员");
    }
}
