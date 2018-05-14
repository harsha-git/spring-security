package org.baeldung.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by harsjaya on 5/5/18.
 */
@Aspect
public class TenantLevelRbacAdvice {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMappingAnnotatedPointcut() {
    }

    @Around("effectivePointcut()")
    public Object tenantContextExtractionAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object retObj = joinPoint.proceed();

        return retObj;
    }

}
