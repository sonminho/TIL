package ch3.item14;

import java.util.Comparator;

public final class PhoneNumber implements Cloneable {

    private final int areaCode, prefix, lineNum;
    private int hashCode;

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = areaCode;
        this.prefix = prefix;
        this.lineNum = lineNum;
    }

    private static int rangeCheck(int val, int max, String arg) {
        if(val < 0 || val > max) {
            throw new IllegalArgumentException(arg + " : " + val);
        }
        return val;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof PhoneNumber)) return false;
        PhoneNumber pn = (PhoneNumber) obj;
        return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
    }

    @Override
    public int hashCode() {
        int result = hashCode;

        if(result == 0) { // 스레드 안정성 고려
            result = Short.hashCode((short)areaCode);
            result = 31 * result + Short.hashCode((short) prefix);
            result = 31 * result + Short.hashCode((short) lineNum);
        }
        return result;
    }

    /*
     가변상태를 참조하지 않는 clone 메서드
     */
    @Override public PhoneNumber clone() {
        try {
            /*
            재정의한 메서드의 반환 타입은 상위 클래스의 메서드가
            반환하는 하위 타입일 수 있다.
            -> 클라이언트가 형변환하지 않아도 된다.
             */
            return (PhoneNumber) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Cloneable 인터페이스를 구현하면 발생하지 않음
        }
    }

    @Override
    public String toString() {
        return areaCode + "-" + prefix + "-" + lineNum;
    }

    private static final Comparator<PhoneNumber> COMPARATOR = Comparator
            .comparingInt((PhoneNumber pn) -> pn.areaCode)
            .thenComparingInt(pn -> pn.prefix)
            .thenComparingInt(pn -> pn.lineNum);

    public int compareTo(PhoneNumber pn) {
        return COMPARATOR.compare(this, pn);
    }
}
