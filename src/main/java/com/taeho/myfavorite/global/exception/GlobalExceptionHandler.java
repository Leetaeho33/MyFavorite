package com.taeho.myfavorite.global.exception;

import com.taeho.myfavorite.global.dto.CommonResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<CommonResponseDTO> handleException(IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).
                body(new CommonResponseDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public ResponseEntity<CommonResponseDTO> handleException(MethodArgumentNotValidException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).
//                body(new CommonResponseDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
//    }
}
