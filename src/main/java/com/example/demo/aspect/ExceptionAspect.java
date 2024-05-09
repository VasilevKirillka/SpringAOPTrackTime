package com.example.demo.aspect;

import com.example.demo.exception.ToDoException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Component
@Aspect
@Slf4j
public class ExceptionAspect {

    @AfterThrowing(pointcut = "execution(* com.example.demo.service.ToDoService.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Exception error) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error("В методе {} произошла ошибка: {}", methodName, error.getMessage());

    }
}
