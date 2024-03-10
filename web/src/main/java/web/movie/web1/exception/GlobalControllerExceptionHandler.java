package web.movie.web1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import web.movie.web1.model.respone.ErrorResponse;

import java.util.zip.DataFormatException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handlerBadRequestException(BadRequestException e){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);//400
    }
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> handlerNotFoundRequestException(ResourceNotFound e){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);//404
    }
    @ExceptionHandler(Exception.class)// xử lý các excertion được khai báo
    public ResponseEntity<?> handlerException(Exception e){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
