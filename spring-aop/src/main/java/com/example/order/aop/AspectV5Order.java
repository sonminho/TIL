package com.example.order.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
@RequiredArgsConstructor

public class AspectV5Order {

    @Aspect
    @Order(2)
    public static class LogAspect {
        @Around("com.example.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[log] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

    @Aspect
    @Order(1)
    public static class TransactionAspect {
        // com.example.order 패키지아 하위 패키지이면서 클래스 이름의 패턴이 *Service
        @Around("com.example.order.aop.Pointcuts.orderAndService()")
        public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
            try {
                log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
                Object result = joinPoint.proceed();
                log.info("[트랜잭션 끝] {}", joinPoint.getSignature());
                return  result;
            } catch(Exception e) {
                log.error("[트랜잭션 롤백] {}", joinPoint.getSignature());
                throw e;
            } finally {
                log.error("[리소스 릴리즈] {}", joinPoint.getSignature());
            }
        }
    }

}
