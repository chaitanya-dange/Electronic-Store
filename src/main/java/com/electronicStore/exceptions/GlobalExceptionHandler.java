package com.electronicStore.exceptions;

import com.electronicStore.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        logger.info("Exception in Global Exception");
        ApiResponseMessage status = ApiResponseMessage.builder().message(ex.getMessage()).success(true).build();
        return  new ResponseEntity<>(status,HttpStatus.NOT_FOUND);

    }

    //Method Argument Not Valid Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        Map<String,Object> response =new HashMap<>();
        allErrors.stream().forEach(objectError -> {
            // message is value and field is key , these are key-value pair
            String message=objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field,message);
        });

        return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    // to handle file related extension
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage> badApiRequestExceptionHandler(BadApiRequest ex){
        logger.info("Bad API request !! ");
        ApiResponseMessage status = ApiResponseMessage.builder().message(ex.getMessage()).success(false).build();
        return  new ResponseEntity<>(status,HttpStatus.BAD_REQUEST);

    }
}
