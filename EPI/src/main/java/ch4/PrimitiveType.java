package ch4;

/*
 * 기본자료형 연습
 */
public class PrimitiveType {

    /*
        값이 1인 Bit 수 세기
        최하위 bit 부터 shift 연산을 한 번씩 진행
        bit 수가 n개일 때, 시간 복잡도는 O(n)
     */
    public short countBits(int x) {
        short bitCnt = 0;

        while(x != 0) {
            bitCnt += (x & 1);
            x >>>= 1;
        }

        return bitCnt;
    }

}
