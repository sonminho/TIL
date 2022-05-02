package ch3.item13;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack implements Cloneable {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INIT_SIZE = 16;

    public Object[] getElements() {
        return elements;
    }

    public Stack() {
        this.elements = new Object[DEFAULT_INIT_SIZE];
    }

    public void push(Object obj) {
        elements[size++] = obj;
    }
    
    public Object pop() {
        if(size == 0) throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null; // 다쓴 객체 참조해제
        return result;
    }

    // 원소를 위한 공간을 확보
    private void ensureCapacity() {
        if(elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size +1);
    }

    @Override
    public Stack clone() {
        try {
            Stack clone = (Stack) super.clone();
            clone.elements = elements.clone(); // 생략시 원본이 변경되면 복사본도 변함
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
