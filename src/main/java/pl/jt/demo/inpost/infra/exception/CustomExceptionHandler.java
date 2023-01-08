package pl.jt.demo.inpost.infra.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.jt.demo.inpost.infra.response.ExceptionResponse;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(value = {NoSuchProductFoundException.class, NoSuchDiscountPolicyFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNoProductAndNoPolicyExceptions(Exception e) {
        log.error(e.getMessage(), e);
        return new ExceptionResponse(e.getMessage());
    }

    @ExceptionHandler(value = {InvalidValueProvidedException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleInvalidValueProvidedExceptionClass(Exception e) {
        log.error(e.getMessage(), e);
        return new ExceptionResponse(e.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleOtherExceptions(Exception e) {
        log.error(e.getMessage(), e);
        return new ExceptionResponse(e.getMessage());
    }

}
