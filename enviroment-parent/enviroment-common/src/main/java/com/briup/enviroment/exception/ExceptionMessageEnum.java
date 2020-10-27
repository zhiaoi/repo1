package com.briup.enviroment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessageEnum {
    PARM_IS_NULL("参数为空");
    
    private String message;
}
