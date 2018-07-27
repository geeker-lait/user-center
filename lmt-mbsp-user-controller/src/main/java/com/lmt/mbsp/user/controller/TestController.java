package com.lmt.mbsp.user.controller;

import com.lmt.framework.core.exception.ControllerException;
import com.lmt.framework.core.exception.code.ControllerErrorCode;
import com.lmt.framework.support.model.message.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @RequestMapping(value = "/test1/{error}", method = RequestMethod.GET)
    public ResponseMessage test2(@PathVariable(value = "error") String error){

        log.info("error={}",error);

        switch (error){
            case "1":
                throw new ControllerException(ControllerErrorCode.CONTROLLER_ERROR, ControllerErrorCode.CONTROLLER_ERROR.toString());
            case "2":
                throw new ControllerException(ControllerErrorCode.ILLEGAL_PARAMS, ControllerErrorCode.ILLEGAL_PARAMS.toString());
            case "3":
                throw new ControllerException(ControllerErrorCode.NOT_FOUND, ControllerErrorCode.NOT_FOUND.toString());
            case "4":
                throw new ControllerException(ControllerErrorCode.BUSINESS_ERROR, ControllerErrorCode.BUSINESS_ERROR.toString());
            case "5":
                throw new ControllerException(ControllerErrorCode.DATABASE_ERROR, ControllerErrorCode.DATABASE_ERROR.toString());
        }

        return ResponseMessage.error("悲剧啦");
    }
}
