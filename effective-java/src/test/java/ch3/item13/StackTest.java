package ch3.item13;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    @Test
    public void stackCloneTest() {
        // given
        Stack stack = new Stack();
        stack.push(1);
        stack.push(2);

        // when
        Stack stack2 = stack.clone();
        stack2.push(3);

        // then
        assertNotEquals(stack.getElements(), stack2.getElements());
    }

}