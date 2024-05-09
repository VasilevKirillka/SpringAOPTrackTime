package com.example.demo.aspect;

import com.example.demo.exception.ToDoException;
import com.example.demo.service.TimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
@Order
public class TrackTimeAspect {

    private final TimeService timeService;

    @Pointcut("execution(@com.example.demo.annotation.TrackAsyncTime public * add*(..))")
    public void trackAsyncTimePointCut() {
    }

    @Pointcut("execution(@com.example.demo.annotation.TrackTime public * get*(..))")
    public void trackTimePointCut() {
    }

    @Around("trackAsyncTimePointCut()")
    public Object trackAsyncTimeRunner(ProceedingJoinPoint joinPoint) {
        var res = CompletableFuture.supplyAsync(() -> {
            try {
                String methodName = joinPoint.getSignature().getName();
                long startTime = System.currentTimeMillis();
                log.info("Асинхронный запуск метода {} с TrackAsyncTime", methodName);
                var result = joinPoint.proceed();
                long endTime = System.currentTimeMillis() - startTime;
                timeService.addTime(methodName, endTime);
                log.info("Метод {} выполнился за {} мс ", methodName, endTime);
                return result;
            } catch (Throwable e) {
                log.error("Ошибка TrackAsyncTime");
                throw new ToDoException(e.getMessage());
            }
        });
        return res.join();
    }

    @Around("trackTimePointCut()")
    public Object trackTimeRunner(ProceedingJoinPoint joinPoint) {
        try {
            long startTime = System.currentTimeMillis();
            String methodName = joinPoint.getSignature().getName();
            Object result = joinPoint.proceed();
            log.info("Запуск метода {} с TrackTime", methodName);
            joinPoint.proceed();
            long endTime = System.currentTimeMillis() - startTime;
            timeService.addTime(methodName, endTime);

            log.info("Метод {} выполнился за {} мс", methodName, endTime);
            return result;
        } catch (Throwable e) {
            log.error("Ошибка TrackTime");
            throw new ToDoException(e.getMessage());
        }
    }

}
