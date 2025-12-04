package com.foolxing.mall.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Pointcut(value="execution(* com.foolxing.mall.service.*.*(..))")
    public void recordTimeLog(){}

    @Around(value = "recordTimeLog()")
    public Object Around(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("=====开始执行{}的{}=====",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName());
        Long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long takeTime = System.currentTimeMillis() - begin;
        if (takeTime >= 3000) {
            logger.error("=====执行结束，花费{}毫秒=====",takeTime);
        } else if (takeTime >2000) {
            logger.warn("=====执行结束，花费{}毫秒=====",takeTime);
        } else {
            logger.info("=====执行结束，花费{}毫秒=====",takeTime);
        }
        return result;
    }

    @AfterThrowing(value = "recordTimeLog()")
    public void AfterThrowing(JoinPoint joinPoint) {
        logger.error("{} 的 {} 方法执行失败！",
                joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName());
        logger.error("异常信息：{}");
    }
}
