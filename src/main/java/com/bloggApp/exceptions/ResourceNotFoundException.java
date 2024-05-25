package com.bloggApp.exceptions;

public class ResourceNotFoundException extends RuntimeException{

//    String resourceName;
//
//    String filedName;
//
//    long filedValue;


    public ResourceNotFoundException(String message) {
        super(message);
    }
}
