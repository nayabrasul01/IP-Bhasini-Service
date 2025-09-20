package com.esic.checklist.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.esic.checklist.dto.LoginRequest;

import java.util.Arrays;

@Aspect
@Component
public class TraceableAspect {

    private static final Logger logger = LoggerFactory.getLogger(TraceableAspect.class);

    @Around("@annotation(com.esic.checklist.annotations.Traceable)")
    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
        String requestId = MDC.get("requestId"); // Retrieve from MDC
        long start = System.currentTimeMillis();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        Object[] args = joinPoint.getArgs();

     // Sanitize arguments
        Object[] sanitizedArgs = Arrays.stream(args)
                .map(arg -> {
                    if (arg instanceof LoginRequest) {
                        LoginRequest loginRequest = (LoginRequest) arg;
                        return String.format("LoginRequest(username=%s, password=********)", loginRequest.getUsername());
                    }
                    return arg;
                })
                .toArray();
        logger.info("Enter {} :: {} | args: {}", className, methodName, Arrays.toString(sanitizedArgs));
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable t) {
            logger.error("ERROR in {} :: {} | message: {}", className, methodName, t.getMessage());
            throw t;
        }
        long duration = System.currentTimeMillis() - start;
        logger.info("Exit {} :: {} | result: {} | Execution Time: {}ms", className, methodName, result, duration);
        return result;
    }
}

