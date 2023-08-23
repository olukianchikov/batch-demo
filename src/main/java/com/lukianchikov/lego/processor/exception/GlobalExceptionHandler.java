package com.lukianchikov.lego.processor.exception;

import com.lukianchikov.lego.processor.dto.JobNotSubmitted;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JobInstanceAlreadyCompleteException.class)
    public ResponseEntity<Object> handle(JobInstanceAlreadyCompleteException exception) {
        JobNotSubmitted response = new JobNotSubmitted();
        response.setMessage("You requested to process the file that has already been processed.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
