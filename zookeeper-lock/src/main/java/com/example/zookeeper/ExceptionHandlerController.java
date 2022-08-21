package com.example.zookeeper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yujiale
 */
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object exceptionHandler(RuntimeException e) {

        Map<String, Object> result = new HashMap<>(2);
        result.put("status", "error");
        result.put("message", e.getMessage());
        return result;
    }
}
