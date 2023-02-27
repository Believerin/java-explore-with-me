package ru.practicum.mainservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public  Map<String, Object> handleNotFoundBody(final NoSuchBodyException e) {
        return e.getParameter();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleMismatchRequest(final MethodArgumentTypeMismatchException e) {
        String message = e.getMessage();
        return Map.of("errors", List.of(e.getStackTrace()),
                "message", message != null ? message : "Не пройдена валидация",
                "reason", "Неправильно составлен запрос",
                "status", HttpStatus.BAD_REQUEST.name(),
                "timeStamp", LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus
    public ResponseEntity<Map<String, Object>> handleInvalidBody(final MethodArgumentNotValidException e) {
        String validationType = e.getBindingResult().getAllErrors().iterator().next().getCode();
        HttpStatus httpStatus;
        if (validationType != null && validationType.equals("StartInGivenHoursValid")) {
            httpStatus = HttpStatus.CONFLICT;
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        String message = e.getBindingResult().getAllErrors().listIterator().next().getDefaultMessage();
        return new ResponseEntity<>(Map.of("errors", List.of(e.getStackTrace()),
                "message", message != null ? message : "Не пройдена валидация",
                "reason", "Неправильно составлен запрос",
                "status", httpStatus.name(),
                "timeStamp", LocalDateTime.now()), httpStatus);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public  Map<String, Object> handleNoAccess(final NoAccessException e) {
        return e.getParameter();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public  Map<String, Object> handleNotFoundStatus(final UnsupportedStatusException e) {
        return e.getParameter();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public  Map<String, Object> handleReachedLimitBody(final ReachedLimitException e) {
        return e.getParameter();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public  Map<String, Object> handleInvalidRequest(final InvalidRequestException e) {
        return e.getParameter();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleInvalidBody(final MissingServletRequestParameterException e) {
        String message = e.getMessage();
        return Map.of("errors", List.of(e.getStackTrace()),
                "message", message != null ? message : "Не пройдена валидация",
                "reason", "Неправильно составлен запрос",
                "status", HttpStatus.BAD_REQUEST.name(),
                "timeStamp", LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleViolatedIntegrity(final DataIntegrityViolationException e) {
        String message = e.getMessage();
        return (Map.of("errors", e.getStackTrace(),
                "message", message != null ? message : "Не пройдена валидация",
                "reason","Нарушена целостность данных",
                "status", HttpStatus.CONFLICT.name(),
                "timeStamp", LocalDateTime.now()));
    }
}