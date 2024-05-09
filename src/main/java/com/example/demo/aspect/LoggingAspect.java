package com.example.demo.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class LoggingAspect {

    @Before("execution(* com.example.demo.service.ToDoService.*(..)) " +
            "|| execution(* com.example.demo.service.TimeService.get*(..))")
    public void logBefore(JoinPoint joinPoint) {
        Object[] methodsArgs = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        log.info("Вызов метода {} с аргументами {}", methodName, methodsArgs);
    }

    @AfterReturning(pointcut = "execution(* com.example.demo.service.ToDoService.*(..)) " +
            "|| execution(* com.example.demo.service.TimeService.get*(..))",
            returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Метод {} вернул результат {}", methodName, result);
    }
}
