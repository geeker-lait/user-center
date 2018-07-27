package com.lmt.mbsp.user.controller.exception;

import com.lmt.framework.core.exception.BusinessException;
import com.lmt.framework.core.exception.ControllerException;
import com.lmt.framework.core.exception.NotFoundException;
import com.lmt.framework.core.exception.code.ControllerErrorCode;
import com.lmt.framework.support.model.message.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lex on 2018/7/16.
 * RestExceptionHandler
 */
@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

    @ExceptionHandler(ControllerException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private <T> ResponseMessage<T> controllerExceptionHandler(ControllerException e){
        log.error("---------> controller error!", e);
        return ResponseMessage.error(ControllerErrorCode.CONTROLLER_ERROR.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private <T> ResponseMessage<T> illegalParamsExceptionHandler(MethodArgumentNotValidException e) {
        log.error("---------> invalid request!", e);
        return ResponseMessage.error(ControllerErrorCode.ILLEGAL_PARAMS.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private <T> ResponseMessage<T> buisinessExceptionHandler(MethodArgumentNotValidException e) {
        log.error("---------> business error!", e);
        return ResponseMessage.error(ControllerErrorCode.BUSINESS_ERROR.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private <T> ResponseMessage<T> notFoundExceptionHandler(MethodArgumentNotValidException e) {
        log.error("---------> not found!", e);
        return ResponseMessage.error(ControllerErrorCode.NOT_FOUND.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private <T> ResponseMessage<T> unkonwnExceptionHandler(MethodArgumentNotValidException e) {
        log.error("---------> unknown error!", e);
        return ResponseMessage.error(ControllerErrorCode.UNKNOWN_ERROR.getMessage());
    }
}
