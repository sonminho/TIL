package behavior;

import behavioral.beverage.Beverage;
import behavioral.beverage.Espresso;
import behavioral.condiment.Mocha;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StarbucksCoffee {

    @DisplayName("커피생성 테스트")
    @Test
    public void beverageTest() throws Exception {
        // given
        Beverage espresso = new Espresso();
        System.out.println(espresso.getDescription() + " $" + espresso.cost());

        Beverage mocha = new Mocha(espresso);
        System.out.println(mocha.getDescription() + " $" + mocha.cost());

        // when
        double totalPrice = mocha.cost();

        // then
        Assertions.assertEquals(totalPrice, espresso.cost() + .20);
    }

}
