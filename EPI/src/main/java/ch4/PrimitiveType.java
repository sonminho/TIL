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

    /*
        4.1 패리티 계산
        bit 의 1의 개수가 짝수면 0, 홀수면 1을 반환
        1로 셋팅된 bit 수가 k 개라면
        시간 복잡도는 O(k)
     */
    public short parity(long x) {
        short result = 0;

        // 1로된 최하위 비트를 제거
        while(x != 0) {
            result ^= 1; // XOR 0, 1 반복
            x &= (x-1);
        }

        return result;
    }

    /*
        4.2 비트스왑
        입력 크기에 상관없이 O(1)
     */
    public long swapBits(long x, int i, int j) {
        // i번째 비트와 j번째 비트가 다르면 스왑
        if( ((x >>> i) & 1) != ((x >>> j) & 1) ) {
            long bitMask = (1L << i) | (1L << j);
            x ^= bitMask; // XOR
        }

        return x;
    }
}
