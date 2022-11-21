package com.example.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepository {

    public String save(String itemId) {
        log.info("[orderRepository] 실행");
        if(itemId.equals("ex"))
            throw new IllegalStateException("예외 발생");

        return "ok";
    }

}
