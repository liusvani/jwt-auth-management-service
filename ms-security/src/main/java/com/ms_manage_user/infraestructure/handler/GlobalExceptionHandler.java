package com.ms_manage_user.infraestructure.handler;

import com.ms_manage_user.infraestructure.controller.dto.output.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*@ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(new ErrorResponse(ex.getReason()));
    }*/

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleNotFound(ResponseStatusException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        return new ResponseEntity<>("" + ex.getReason(), headers, ex.getStatusCode());
    }
    //Controllar error de deserialize del enum
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleEnumParseError(HttpMessageNotReadableException ex) {
        String mensaje = "- Invalid role. Select ADMIN or USER.";
        return ResponseEntity.badRequest()
                .contentType(MediaType.TEXT_PLAIN)
                .body(mensaje);
    }
}

