package site.bitinit.pnd.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.bitinit.pnd.web.controller.dto.ResponseDto;

/**
 * @author john
 * @date 2020-01-11
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<ResponseDto> dataFormatException(DataFormatException e){
        return ResponseEntity.badRequest()
                .body(ResponseDto.fail(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> dataNotFoundException(DataNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ResponseDto.fail(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> exception(Exception e){
        logger.error("[system-error] {}", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDto.fail("服务器错误，请联系管理员"));
    }
}
