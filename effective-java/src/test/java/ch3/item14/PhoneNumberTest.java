package ch3.item14;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {

    @Test
    public void compareTest() {
        PhoneNumber pn1 = new PhoneNumber(02,111,222);
        PhoneNumber pn2 = new PhoneNumber(03,111,222);
        PhoneNumber pn3 = new PhoneNumber(02,111,220);
        PhoneNumber pn4 = new PhoneNumber(02,111,222);
        List<PhoneNumber> phoneNumberList = new ArrayList<>();
        phoneNumberList.add(pn1);
        phoneNumberList.add(pn2);
        phoneNumberList.add(pn3);
        phoneNumberList.add(pn4);

        assertTrue(pn1.compareTo(pn2) < 0);
        assertTrue(pn1.compareTo(pn3) > 0);
        assertTrue(pn3.compareTo(pn4) < 0);
        assertEquals(0, pn1.compareTo(pn4));
        phoneNumberList.sort(PhoneNumber::compareTo);
        System.out.println(phoneNumberList.toString());
    }
}