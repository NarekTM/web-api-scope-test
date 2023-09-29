package com.thevirtugroup.postitnote.advice;

import com.thevirtugroup.postitnote.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@ControllerAdvice
public class ApiErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiErrorHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleEntityNotFoundException(HttpServletRequest servletRequest, EntityNotFoundException ex) {
        String exceptionMessage = ex.getMessage();
        String httpMethodName = servletRequest.getMethod();
        String servletPath = servletRequest.getServletPath();
        log(httpMethodName, servletPath, exceptionMessage, ex.getStackTrace());

        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<String> handleOtherExceptions(HttpServletRequest servletRequest, Throwable ex) {
        String message = ex.getMessage();
        String method = servletRequest.getMethod();
        String servletPath = servletRequest.getServletPath();
        log(method, servletPath, message, ex.getStackTrace());

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static void log(String httpMethodName, String servletPath, String exceptionMessage, StackTraceElement[] ex) {
        LOGGER.info(String.format("Exception occurred: request path = %s %s, message = %s\nException stack trace: %s",
                httpMethodName, servletPath, exceptionMessage, Arrays.toString(ex)));
    }
}
