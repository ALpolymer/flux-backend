package gr.aueb.cf.flux.core;

import gr.aueb.cf.flux.core.exceptions.*;
import gr.aueb.cf.flux.dto.ResponseMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String > handleValidationException(ValidationException e){
        log.warn("validation failed. Message={}", e.getMessage());

        BindingResult bindingResult = e.getBindingResult();

        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()){
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return errors;
    }

    @ExceptionHandler(AppObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseMessageDTO handleNotFound(AppObjectNotFoundException e) {
        log.warn("Entity not found: {}", e.getMessage());

        return new ResponseMessageDTO(e.getCode(), e.getMessage());
    }


    @ExceptionHandler(AppObjectInvalidArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessageDTO handleInvalidArgument(AppObjectInvalidArgumentException e){
        log.warn("Invalid Argument: {}", e.getMessage());

        return new ResponseMessageDTO(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(AppObjectAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseMessageDTO handleAlreadyExists(AppObjectAlreadyExists e){
        log.warn("Entity already exists: {}", e.getMessage());

        return new ResponseMessageDTO(e.getCode(), e.getMessage());

    }
    @ExceptionHandler(AppObjectNotAuthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseMessageDTO handleNotAuthorized(AppObjectNotAuthorizedException e, WebRequest request){
        log.warn("Authorization failed for URI={}. Message={}", request.getDescription(false), e.getMessage());

        return new ResponseMessageDTO(e.getCode(), e.getMessage());

    }

    @ExceptionHandler(AppServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessageDTO handleServerError(AppServerException e){
        log.warn("Server error: {}", e.getMessage());

        return new ResponseMessageDTO(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseMessageDTO handleAccessDenied(AccessDeniedException e){

        return new ResponseMessageDTO("ACCESS_DENIED", e.getMessage());

    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessageDTO handleDatabaseErrors(DataAccessException e){

        return new ResponseMessageDTO("DATABASE_ERROR", e.getMessage());
    }
}
