package com.example.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.example.order..*(..))")
    public void allOrder() {} // 포인트컷 시그니쳐

    // 클래스 이름의 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}

}
