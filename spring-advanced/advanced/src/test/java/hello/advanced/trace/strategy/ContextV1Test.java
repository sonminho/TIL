package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.stragtegy.ContextV1;
import hello.advanced.trace.strategy.code.stragtegy.Strategy;
import hello.advanced.trace.strategy.code.stragtegy.StrategyLog1;
import hello.advanced.trace.strategy.code.stragtegy.StrategyLog2;
import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    @Test
    void strategyV1() {
        StrategyLog1 strategyLog1 = new StrategyLog1();
        ContextV1 contextV1 = new ContextV1(strategyLog1);
        contextV1.execute();

        StrategyLog2 strategyLog2 = new StrategyLog2();
        ContextV1 contextV2 = new ContextV1(strategyLog2);
        contextV2.execute();
    }

    @Test
    void strategyV2() {
        Strategy strategyLog1 = new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직1 실행");
            }
        };
        ContextV1 contextV1 = new ContextV1(strategyLog1);
        contextV1.execute();

        Strategy strategyLog2 = new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직2 실행");
            }
        };
        ContextV1 contextV2 = new ContextV1(strategyLog2);
        contextV2.execute();
    }

    @Test
    void strategy3() {
        ContextV1 contextV1 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직3 실행");
            }
        });

        contextV1.execute();
    }

    @Test
    void strategy4() {
        ContextV1 contextV1 = new ContextV1(()-> log.info("비지니스 로직4 실행"));
        contextV1.execute();
    }

}
