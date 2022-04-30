package ch3.item13;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {

    @Test
    public void cloneTest() {
        PhoneNumber p = new PhoneNumber(1,2,3);
        assertEquals(p, p.clone());
        assertEquals(p.getClass(), p.clone().getClass());
    }
}