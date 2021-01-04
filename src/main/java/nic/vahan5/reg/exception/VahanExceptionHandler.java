package nic.vahan5.reg.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import nic.vahan5.reg.common.VahanException.VahanException;

/**
 *
 * @author Kartikey Singh
 */
@ControllerAdvice
public class VahanExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {VahanException.class})
    protected ResponseEntity<Object> handleConflict(VahanException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), ex.getCode(), request);
    }
}
