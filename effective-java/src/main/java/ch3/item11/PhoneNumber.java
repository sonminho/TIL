package ch3.item11;

public final class PhoneNumber {

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
}
