package ch8.item49;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void paramTest() {
        Student student = new Student();

        try {
            assert student.getAddress() != null;
        } catch (AssertionError e) {
            assertNotNull(e);
        }
    }
}