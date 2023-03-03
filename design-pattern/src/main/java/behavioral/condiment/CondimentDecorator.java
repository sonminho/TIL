package behavioral.condiment;

import behavioral.beverage.Beverage;

public abstract class CondimentDecorator extends Beverage {

    /**
     * 각 데코레이터가 감쌀 Beverage 객체
     */
    Beverage beverage;

    /**
     * 첨가물 데코레이터에서 구현
     */
    public abstract String getDescription();

}
