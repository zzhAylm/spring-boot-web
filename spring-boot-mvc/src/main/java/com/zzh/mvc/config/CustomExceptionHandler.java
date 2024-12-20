package com.zzh.mvc.config;

import com.zzh.springboot3.common.dto.ResponseDto;
import com.zzh.springboot3.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/20 17:18
 */
@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseDto<?> MethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : exception.getAllErrors()) {
            sb.append('[').append(error).append("] ");
        }
        return ResponseDto.fail(exception.getStatusCode().value(), sb.toString());
    }


}
