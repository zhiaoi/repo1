package com.briup.enviroment.exception;

public class EnviromentException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;
    private String message;
   
    public EnviromentException(String message) {
        super(message);
    }
    
    public EnviromentException() {
        super();
    }

}
