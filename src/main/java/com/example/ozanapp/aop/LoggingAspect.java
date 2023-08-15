package com.example.ozanapp.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Around("execution(* com.example.ozanapp.service.*.*(..)) ")
    public Object logBeforeAndAfterMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String targetClass = joinPoint.getTarget().getClass().getSimpleName();
        String targetMethod = joinPoint.getSignature().getName();

        log.info("Executing {}.{}", targetClass, targetMethod);
        for (Object arg : joinPoint.getArgs()) {
            log.info("Method arguments: {}", arg);
        }
        log.info("Method returned: {}", joinPoint.proceed());

        return joinPoint.proceed();
    }
}