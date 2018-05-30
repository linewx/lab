package com.linewx.law.instrument;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by luganlin on 12/18/16.
 */
@Aspect
@Component
public class TimeAspect {
    @Around("execution(* com.linewx.law.instrument.audit.AuditService.getResult(..))")
    public void printTime(JoinPoint joinPoint) {
        System.out.println("current time" + System.currentTimeMillis());
    }
}
