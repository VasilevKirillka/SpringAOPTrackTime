package com.example.demo.exception;

import com.example.demo.annotation.Throw;

@Throw
public class ToDoException extends RuntimeException {
    public ToDoException() {
    }

    public ToDoException(String message) {
        super(message);
    }
}
